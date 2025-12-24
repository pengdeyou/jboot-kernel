

package org.jboot.kernel.log.publisher;

import org.jboot.kernel.log.constant.EventConstant;
import org.jboot.kernel.log.event.ErrorLogEvent;
import org.jboot.kernel.log.entity.LogAbstract;
import org.jboot.kernel.log.entity.LogError;
import org.jboot.kernel.log.utils.LogAbstractUtil;
import org.jboot.kernel.toolkit.utils.Exceptions;
import org.jboot.kernel.toolkit.utils.Func;
import org.jboot.kernel.toolkit.utils.SpringUtil;
import org.jboot.kernel.toolkit.utils.WebUtil;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常信息事件发送
 *
 * @author Chill
 */
public class ErrorLogPublisher {

	public static void publishEvent(Throwable error, String requestUri) {
		HttpServletRequest request = WebUtil.getRequest();
		LogError logError = new LogError();
		logError.setRequestUri(requestUri);
		if (Func.isNotEmpty(error)) {
			logError.setStackTrace(Exceptions.getStackTraceAsString(error));
			logError.setExceptionName(error.getClass().getName());
			logError.setMessage(error.getMessage());
			StackTraceElement[] elements = error.getStackTrace();
			if (Func.isNotEmpty(elements)) {
				StackTraceElement element = elements[0];
				logError.setMethodName(element.getMethodName());
				logError.setMethodClass(element.getClassName());
				logError.setFileName(element.getFileName());
				logError.setLineNumber(element.getLineNumber());
			}
		}
		LogAbstractUtil.addRequestInfoToLog(request, logError);

		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logError);
		event.put(EventConstant.EVENT_REQUEST, request);
		SpringUtil.publishEvent(new ErrorLogEvent(event));
	}

}

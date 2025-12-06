

package org.jboot.kernel.log.publisher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboot.kernel.log.annotation.ApiLog;
import org.jboot.kernel.log.constant.EventConstant;
import org.jboot.kernel.log.event.ApiLogEvent;
import org.jboot.kernel.log.entity.LogAbstract;
import org.jboot.kernel.log.entity.LogApi;
import org.jboot.kernel.log.utils.LogAbstractUtil;
import org.jboot.kernel.tool.constant.BladeConstant;
import org.jboot.kernel.tool.utils.SpringUtil;
import org.jboot.kernel.tool.utils.WebUtil;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * API日志信息事件发送
 *
 * @author Chill
 */
public class ApiLogPublisher {

	public static void publishEvent(String methodName, String methodClass, ApiLog apiLog, long time) {
		HttpServletRequest request = WebUtil.getRequest();
		LogApi logApi = new LogApi();
		logApi.setType(BladeConstant.LOG_NORMAL_TYPE);
		logApi.setTitle(apiLog.value());
		logApi.setTime(String.valueOf(time));
		logApi.setMethodClass(methodClass);
		logApi.setMethodName(methodName);

		LogAbstractUtil.addRequestInfoToLog(request, logApi);
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logApi);
		SpringUtil.publishEvent(new ApiLogEvent(event));
	}

}



package org.jboot.kernel.log.publisher;

import org.jboot.kernel.log.constant.EventConstant;
import org.jboot.kernel.log.event.UsualLogEvent;
import org.jboot.kernel.log.entity.LogAbstract;
import org.jboot.kernel.log.entity.LogUsual;
import org.jboot.kernel.log.utils.LogAbstractUtil;
import org.jboot.kernel.toolkit.utils.SpringUtil;
import org.jboot.kernel.toolkit.utils.WebUtil;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * BLADE日志信息事件发送
 *
 * @author Chill
 */
public class UsualLogPublisher {

	public static void publishEvent(String level, String id, String data) {
		HttpServletRequest request = WebUtil.getRequest();
		LogUsual logUsual = new LogUsual();
		logUsual.setLogLevel(level);
		logUsual.setLogId(id);
		logUsual.setLogData(data);

		LogAbstractUtil.addRequestInfoToLog(request, logUsual);
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logUsual);
		event.put(EventConstant.EVENT_REQUEST, request);
		SpringUtil.publishEvent(new UsualLogEvent(event));
	}

}

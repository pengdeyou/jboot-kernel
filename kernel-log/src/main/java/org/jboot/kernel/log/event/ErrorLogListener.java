
package org.jboot.kernel.log.event;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboot.kernel.launch.props.JProperties;
import org.jboot.kernel.launch.server.ServerInfo;
import org.jboot.kernel.log.constant.EventConstant;
import org.jboot.kernel.log.feign.ILogClient;
import org.jboot.kernel.log.entity.LogError;
import org.jboot.kernel.log.utils.LogAbstractUtil;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * 异步监听错误日志事件
 *
 * @author Chill
 */
@Slf4j
@AllArgsConstructor
public class ErrorLogListener {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final JProperties bladeProperties;

	@Async
	@Order
	@EventListener(ErrorLogEvent.class)
	public void saveErrorLog(ErrorLogEvent event) {
		try {
			Map<String, Object> source = (Map<String, Object>) event.getSource();
			LogError logError = (LogError) source.get(EventConstant.EVENT_LOG);
			LogAbstractUtil.addOtherInfoToLog(logError, bladeProperties, serverInfo);
			logService.saveErrorLog(logError);
		} catch (Exception e) {
			log.error("保存错误日志失败", e);
		}
	}

}

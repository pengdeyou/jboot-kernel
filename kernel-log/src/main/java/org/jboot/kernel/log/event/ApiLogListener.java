

package org.jboot.kernel.log.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboot.kernel.launch.props.BladeProperties;
import org.jboot.kernel.launch.server.ServerInfo;
import org.jboot.kernel.log.constant.EventConstant;
import org.jboot.kernel.log.feign.ILogClient;
import org.jboot.kernel.log.entity.LogAbstract;
import org.jboot.kernel.log.entity.LogApi;
import org.jboot.kernel.log.utils.LogAbstractUtil;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;


/**
 * 异步监听日志事件
 *
 * @author Chill
 */
@Slf4j
@AllArgsConstructor
public class ApiLogListener {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final BladeProperties bladeProperties;


	@Async
	@Order
	@EventListener(ApiLogEvent.class)
	public void saveApiLog(ApiLogEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		LogApi logApi = (LogApi) source.get(EventConstant.EVENT_LOG);
		LogAbstractUtil.addOtherInfoToLog(logApi, bladeProperties, serverInfo);
		logService.saveApiLog(logApi);
	}

}

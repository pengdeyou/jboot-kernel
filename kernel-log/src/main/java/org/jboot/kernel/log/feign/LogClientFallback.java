package org.jboot.kernel.log.feign;

import lombok.extern.slf4j.Slf4j;
import org.jboot.kernel.log.entity.LogApi;
import org.jboot.kernel.log.entity.LogError;
import org.jboot.kernel.log.entity.LogUsual;
import org.jboot.kernel.tool.api.R;
import org.springframework.stereotype.Component;

/**
 * 日志fallback
 *
 * @author jiang
 */
@Slf4j
@Component
public class LogClientFallback implements ILogClient {

	@Override
	public R<Boolean> saveUsualLog(LogUsual log) {
		return R.fail("usual log send fail");
	}

	@Override
	public R<Boolean> saveApiLog(LogApi log) {
		return R.fail("api log send fail");
	}

	@Override
	public R<Boolean> saveErrorLog(LogError log) {
		return R.fail("error log send fail");
	}
}

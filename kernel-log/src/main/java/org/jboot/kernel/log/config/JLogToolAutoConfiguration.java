

package org.jboot.kernel.log.config;

import lombok.AllArgsConstructor;
import org.jboot.kernel.launch.props.JProperties;
import org.jboot.kernel.launch.server.ServerInfo;
import org.jboot.kernel.log.aspect.ApiLogAspect;
import org.jboot.kernel.log.event.ApiLogListener;
import org.jboot.kernel.log.event.ErrorLogListener;
import org.jboot.kernel.log.event.UsualLogListener;
import org.jboot.kernel.log.feign.ILogClient;
import org.jboot.kernel.log.logger.JLogger;
import org.jboot.kernel.log.props.JLogProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 日志工具自动配置
 *
 * @author Chill
 */
@AutoConfiguration
@AllArgsConstructor
@ConditionalOnWebApplication
public class JLogToolAutoConfiguration {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final JProperties bladeProperties;

	@Bean
	@ConditionalOnProperty(value = JLogProperties.PREFIX + "api.enabled", havingValue = "true", matchIfMissing = true)
	public ApiLogAspect apiLogAspect() {
		return new ApiLogAspect();
	}

	@Bean
	@ConditionalOnProperty(value = JLogProperties.PREFIX + "api.enabled", havingValue = "true", matchIfMissing = true)
	public ApiLogListener apiLogListener() {
		return new ApiLogListener(logService, serverInfo, bladeProperties);
	}

	@Bean
	@ConditionalOnProperty(value = JLogProperties.PREFIX + "error.enabled", havingValue = "true", matchIfMissing = true)
	public ErrorLogListener errorEventListener() {
		return new ErrorLogListener(logService, serverInfo, bladeProperties);
	}

	@Bean
	@ConditionalOnProperty(value = JLogProperties.PREFIX + "usual.enabled", havingValue = "true", matchIfMissing = true)
	public UsualLogListener bladeEventListener() {
		return new UsualLogListener(logService, serverInfo, bladeProperties);
	}

	@Bean
	@ConditionalOnProperty(value = JLogProperties.PREFIX + "usual.enabled", havingValue = "true", matchIfMissing = true)
	public JLogger bladeLogger() {
		return new JLogger();
	}

}

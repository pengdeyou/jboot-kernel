
package org.jboot.kernel.loadbalancer.rule;

import net.dreamlu.mica.auto.annotation.AutoEnvPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

/**
 * 灰度版本 自动处理
 *
 * @author Corsak
 */
@AutoEnvPostProcessor
public class GrayscaleEnvPostProcessor implements EnvironmentPostProcessor, Ordered {
	private static final String GREYSCALE_KEY = "blade.loadbalancer.version";
	private static final String METADATA_KEY = "spring.cloud.nacos.discovery.metadata.version";

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		String version = environment.getProperty(GREYSCALE_KEY);

		if (StringUtils.hasText(version)) {
			environment.getSystemProperties().put(METADATA_KEY, version);
		}
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

}

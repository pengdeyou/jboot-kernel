
package org.jboot.kernel.swagger;

import org.jboot.kernel.launch.constant.AppConstant;
import org.jboot.kernel.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;

import java.util.Properties;

/**
 * 初始化Swagger配置
 *
 * @author Corsak
 */
public class SwaggerLauncherServiceImpl implements LauncherService {
	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile) {
		Properties props = System.getProperties();
		if (profile.equals(AppConstant.PROD_CODE)) {
			props.setProperty("swagger.enabled", "false");
			props.setProperty("knife4j.enable", "false");
			props.setProperty("knife4j.production", "true");
			props.setProperty("springdoc.api-docs.enabled", "false");
			props.setProperty("springdoc.api-usage.enabled", "false");
			props.setProperty("springdoc.swagger-ui.enabled", "false");
		} else {
			props.setProperty("swagger.enabled", "true");
			props.setProperty("knife4j.enable", "true");
			props.setProperty("knife4j.production", "false");
			props.setProperty("springdoc.api-docs.enabled", "true");
			props.setProperty("springdoc.api-usage.enabled", "true");
			props.setProperty("springdoc.swagger-ui.enabled", "true");
			props.setProperty("spring.mvc.pathmatch.matching-strategy", "ANT_PATH_MATCHER");
		}
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}

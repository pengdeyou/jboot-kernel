
package org.jboot.kernel.tool.config;


import org.jboot.kernel.tool.utils.SpringUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 工具配置类
 *
 * @author Corsak
 */
@AutoConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ToolConfiguration implements WebMvcConfigurer {

	/**
	 * Spring上下文缓存
	 *
	 * @return SpringUtil
	 */
	@Bean
	public SpringUtil springUtil() {
		return new SpringUtil();
	}

}

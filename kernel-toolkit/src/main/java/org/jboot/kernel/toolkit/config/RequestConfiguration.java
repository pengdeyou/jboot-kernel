
package org.jboot.kernel.toolkit.config;

import lombok.AllArgsConstructor;
import org.jboot.kernel.toolkit.request.JRequestFilter;
import org.jboot.kernel.toolkit.request.RequestProperties;
import org.jboot.kernel.toolkit.request.XssProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import jakarta.servlet.DispatcherType;

/**
 * 过滤器配置类
 *
 * @author Corsak
 */
@AutoConfiguration
@AllArgsConstructor
@EnableConfigurationProperties({RequestProperties.class, XssProperties.class})
public class RequestConfiguration {

	private final RequestProperties requestProperties;
	private final XssProperties xssProperties;

	/**
	 * 全局过滤器
	 *
	 * @return 自定义过滤器
	 */
	@Bean
	public FilterRegistrationBean<JRequestFilter> jFilterRegistration() {
		FilterRegistrationBean<JRequestFilter> registration = new FilterRegistrationBean<>();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new JRequestFilter(requestProperties, xssProperties));
		registration.addUrlPatterns("/*");
		registration.setName("jRequestFilter");
		registration.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registration;
	}
}

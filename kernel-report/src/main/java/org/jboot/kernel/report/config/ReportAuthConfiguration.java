
package org.jboot.kernel.report.config;

import jakarta.servlet.Filter;
import org.jboot.kernel.report.filter.UReportAuthFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

/**
 * UReport认证配置类
 *
 * @author JBoot Kernel
 */
@Order
@AutoConfiguration
@ConditionalOnProperty(value = "report.auth", havingValue = "true", matchIfMissing = true)
public class ReportAuthConfiguration {

	@Bean
	public FilterRegistrationBean<Filter> uReportAuthFilter() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new UReportAuthFilter());
		registration.addUrlPatterns("/ureport/*");
		registration.setName("uReportAuthFilter");
		registration.setOrder(1);
		return registration;
	}

}

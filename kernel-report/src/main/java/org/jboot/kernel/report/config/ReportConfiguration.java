
package org.jboot.kernel.report.config;

import com.bstek.ureport.UReportPropertyPlaceholderConfigurer;
import com.bstek.ureport.console.UReportServlet;
import com.bstek.ureport.provider.report.ReportProvider;
import jakarta.servlet.Servlet;
import org.jboot.kernel.report.props.ReportDatabaseProperties;
import org.jboot.kernel.report.props.ReportProperties;
import org.jboot.kernel.report.provider.DatabaseProvider;
import org.jboot.kernel.report.provider.ReportPlaceholderProvider;
import org.jboot.kernel.report.service.IReportFileService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;

/**
 * UReport配置类
 *
 * @author Chill
 */
@Order
@AutoConfiguration
@ConditionalOnProperty(value = "report.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({ReportProperties.class, ReportDatabaseProperties.class})
@ImportResource("classpath:ureport-console-context.xml")
public class ReportConfiguration {

	@Bean
	public ServletRegistrationBean<Servlet> registrationBean() {
		return new ServletRegistrationBean<>(new UReportServlet(), "/ureport/*");
	}

	@Bean
	public UReportPropertyPlaceholderConfigurer uReportPropertyPlaceholderConfigurer(ReportProperties properties) {
		return new ReportPlaceholderProvider(properties);
	}

	@Bean
	@ConditionalOnMissingBean
	public ReportProvider reportProvider(ReportDatabaseProperties properties, IReportFileService service) {
		return new DatabaseProvider(properties, service);
	}

}

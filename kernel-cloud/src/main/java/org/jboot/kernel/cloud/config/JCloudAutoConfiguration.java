
package org.jboot.kernel.cloud.config;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.BlockExceptionHandler;
import feign.Feign;
import feign.RequestInterceptor;
import org.jboot.kernel.cloud.feign.JFeignSentinel;
import org.jboot.kernel.cloud.header.JFeignRequestHeaderInterceptor;
import org.jboot.kernel.cloud.sentinel.JBlockExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;


/**
 * blade cloud 增强配置
 *
 * @author Chill
 */
@AutoConfiguration
@Import(JSentinelFilterConfiguration.class)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class JCloudAutoConfiguration {

	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "feign.sentinel.enabled")
	public Feign.Builder feignSentinelBuilder(RequestInterceptor requestInterceptor) {
		return JFeignSentinel.builder().requestInterceptor(requestInterceptor);
	}

	@Bean
	@ConditionalOnMissingBean
	public BlockExceptionHandler blockExceptionHandler() {
		return new JBlockExceptionHandler();
	}

	@Bean
	@ConditionalOnMissingBean
	public RequestInterceptor requestInterceptor() {
		return new JFeignRequestHeaderInterceptor();
	}

}

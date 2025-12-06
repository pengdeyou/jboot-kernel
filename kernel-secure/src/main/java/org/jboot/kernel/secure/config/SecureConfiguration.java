
package org.jboot.kernel.secure.config;


import lombok.AllArgsConstructor;
import org.jboot.kernel.secure.aspect.AuthAspect;
import org.jboot.kernel.secure.interceptor.ClientInterceptor;
import org.jboot.kernel.secure.interceptor.SecureInterceptor;
import org.jboot.kernel.secure.props.JAuthProperties;
import org.jboot.kernel.secure.props.JSecureProperties;
import org.jboot.kernel.secure.props.JTokenProperties;
import org.jboot.kernel.secure.provider.ClientDetailsServiceImpl;
import org.jboot.kernel.secure.provider.IClientDetailsService;
import org.jboot.kernel.secure.registry.SecureRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 安全配置类
 *
 * @author Chill
 */
@Order
@AutoConfiguration
@AllArgsConstructor
@EnableConfigurationProperties({JAuthProperties.class, JSecureProperties.class, JTokenProperties.class})
public class SecureConfiguration implements WebMvcConfigurer {

	private final SecureRegistry secureRegistry;

	private final JSecureProperties secureProperties;

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		secureProperties.getClient().forEach(cs -> registry.addInterceptor(new ClientInterceptor(cs.getClientId())).addPathPatterns(cs.getPathPatterns()));

		if (secureRegistry.isEnabled()) {
			registry.addInterceptor(new SecureInterceptor())
				.excludePathPatterns(secureRegistry.getExcludePatterns())
				.excludePathPatterns(secureRegistry.getDefaultExcludePatterns())
				.excludePathPatterns(secureProperties.getSkipUrl());
		}
	}

	@Bean
	public AuthAspect authAspect() {
		return new AuthAspect();
	}

	@Bean
	@ConditionalOnMissingBean(IClientDetailsService.class)
	public IClientDetailsService clientDetailsService() {
		return new ClientDetailsServiceImpl(jdbcTemplate);
	}

}

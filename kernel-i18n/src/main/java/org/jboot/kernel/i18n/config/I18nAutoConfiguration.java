
package org.jboot.kernel.i18n.config;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboot.kernel.i18n.interceptor.I18nInterceptor;
import org.jboot.kernel.i18n.props.I18nProperties;
import org.jboot.kernel.i18n.resolver.I18nLocaleResolver;
import org.jboot.kernel.i18n.service.I18nService;
import org.jboot.kernel.toolkit.utils.Func;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * I18n自动配置类
 *
 * @author JBoot Kernel
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(I18nProperties.class)
@ConditionalOnProperty(prefix = I18nProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class I18nAutoConfiguration {

	/**
	 * 类路径前缀常量
	 */
	private static final String CLASSPATH_PREFIX = "classpath:";

	/**
	 * 国际化配置类
	 */
	private final I18nProperties properties;

	/**
	 * 覆盖Spring配置的MessageSource
	 */
	@Bean
	@Primary
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		I18nProperties.MessageSource config = properties.getMessageSource();

		// 设置基础名列表
		String[] baseNames = config.getBaseNames().stream()
			.map(name -> name.startsWith(CLASSPATH_PREFIX) ? name : CLASSPATH_PREFIX + name)
			.toArray(String[]::new);
		messageSource.setBasenames(baseNames);
		// 设置编码
		messageSource.setDefaultEncoding(config.getEncoding());
		// 设置缓存时间
		if (config.getCacheDuration() != null) {
			messageSource.setCacheSeconds(Func.toInt(config.getCacheDuration().getSeconds()));
		}
		// 设置是否使用代码作为默认消息
		messageSource.setUseCodeAsDefaultMessage(config.isUseCodeAsDefaultMessage());
		return messageSource;
	}

	/**
	 * 覆盖Spring配置的Locale解析器
	 */
	@Bean
	@Primary
	public LocaleResolver localeResolver() {
		return new I18nLocaleResolver(properties);
	}

	/**
	 * 配置I18n服务
	 */
	@Bean
	@ConditionalOnMissingBean
	public I18nService i18nService(MessageSource messageSource, LocaleResolver localeResolver) {
		return new I18nService(messageSource, localeResolver, properties);
	}

	/**
	 * 配置I18n拦截器
	 */
	@Bean
	@ConditionalOnMissingBean
	public I18nInterceptor i18nInterceptor() {
		return new I18nInterceptor(properties);
	}

	/**
	 * 注册I18n拦截器
	 */
	@Bean
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	public WebMvcConfigurer i18nWebMvcConfigurer(I18nInterceptor i18nInterceptor) {
		return new WebMvcConfigurer() {
			@Override
			public void addInterceptors(@Nonnull InterceptorRegistry registry) {
				registry.addInterceptor(i18nInterceptor)
					.addPathPatterns("/**")
					.order(0);
			}
		};
	}

}

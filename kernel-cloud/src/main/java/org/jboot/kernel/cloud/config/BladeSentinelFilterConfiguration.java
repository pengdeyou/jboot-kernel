
package org.jboot.kernel.cloud.config;

import com.alibaba.cloud.sentinel.SentinelProperties;
import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.DefaultBlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.config.SentinelWebMvcConfig;
import com.alibaba.csp.sentinel.adapter.web.common.UrlCleaner;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * 处理sentinel2021兼容问题
 *
 * @author Chill
 */
@RequiredArgsConstructor
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class BladeSentinelFilterConfiguration {

	@Bean
	public SentinelWebInterceptor sentinelWebInterceptor(SentinelWebMvcConfig sentinelWebMvcConfig) {
		return new SentinelWebInterceptor(sentinelWebMvcConfig);
	}

	@Bean
	public SentinelWebMvcConfig sentinelWebMvcConfig(SentinelProperties properties,
													 Optional<UrlCleaner> urlCleanerOptional, Optional<BlockExceptionHandler> blockExceptionHandlerOptional,
													 Optional<RequestOriginParser> requestOriginParserOptional) {
		SentinelWebMvcConfig sentinelWebMvcConfig = new SentinelWebMvcConfig();
		sentinelWebMvcConfig.setHttpMethodSpecify(properties.getHttpMethodSpecify());
		sentinelWebMvcConfig.setWebContextUnify(properties.getWebContextUnify());

		if (blockExceptionHandlerOptional.isPresent()) {
			blockExceptionHandlerOptional.ifPresent(sentinelWebMvcConfig::setBlockExceptionHandler);
		} else {
			if (StringUtils.hasText(properties.getBlockPage())) {
				sentinelWebMvcConfig.setBlockExceptionHandler(
					((request, response, s, e) -> response.sendRedirect(properties.getBlockPage())));
			} else {
				sentinelWebMvcConfig.setBlockExceptionHandler(new DefaultBlockExceptionHandler());
			}
		}

		urlCleanerOptional.ifPresent(sentinelWebMvcConfig::setUrlCleaner);
		requestOriginParserOptional.ifPresent(sentinelWebMvcConfig::setOriginParser);
		return sentinelWebMvcConfig;
	}

}

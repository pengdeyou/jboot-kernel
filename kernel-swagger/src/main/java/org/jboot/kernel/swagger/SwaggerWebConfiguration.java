
package org.jboot.kernel.swagger;


import org.jboot.kernel.launch.props.JPropertySource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * swagger资源配置
 *
 * @author Corsak
 */
@AutoConfiguration
@EnableConfigurationProperties(SwaggerProperties.class)
@JPropertySource(value = "classpath:/kernel-swagger.yml")
public class SwaggerWebConfiguration implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}

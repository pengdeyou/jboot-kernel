
package org.jboot.kernel.launch.config;

import lombok.AllArgsConstructor;
import org.jboot.kernel.launch.props.JProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 配置类
 *
 * @author Corsak
 */
@AutoConfiguration
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties({
	JProperties.class
})
public class JLaunchConfiguration {

}

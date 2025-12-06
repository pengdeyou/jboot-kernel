
package org.jboot.kernel.launch.config;

import lombok.AllArgsConstructor;
import org.jboot.kernel.launch.props.BladeProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 配置类
 *
 * @author Chill
 */
@AutoConfiguration
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties({
	BladeProperties.class
})
public class BladeLaunchConfiguration {

}

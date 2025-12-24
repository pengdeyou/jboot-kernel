package org.jboot.kernel.cloud.http;
import org.jboot.kernel.cloud.props.JFeignHeadersProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
@AutoConfiguration
@EnableConfigurationProperties({JHttpProperties.class, JFeignHeadersProperties.class})
public class JHttpConfiguration {
}

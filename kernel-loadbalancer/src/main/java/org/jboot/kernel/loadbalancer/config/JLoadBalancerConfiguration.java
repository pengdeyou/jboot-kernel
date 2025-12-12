
package org.jboot.kernel.loadbalancer.config;

import org.jboot.kernel.loadbalancer.props.JLoadBalancerProperties;
import org.jboot.kernel.loadbalancer.rule.GrayscaleLoadBalancer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientSpecification;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

/**
 * blade 负载均衡策略
 *
 * @author Corsak
 */
@AutoConfiguration
@AutoConfigureBefore(LoadBalancerClientConfiguration.class)
@EnableConfigurationProperties(JLoadBalancerProperties.class)
@ConditionalOnProperty(value = JLoadBalancerProperties.PROPERTIES_PREFIX + ".enabled", matchIfMissing = true)
@Order(JLoadBalancerConfiguration.REACTIVE_SERVICE_INSTANCE_SUPPLIER_ORDER)
public class JLoadBalancerConfiguration {
	public static final int REACTIVE_SERVICE_INSTANCE_SUPPLIER_ORDER = 193827465;

	@Bean
	public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(Environment environment,
																				   LoadBalancerClientFactory loadBalancerClientFactory,
																				   JLoadBalancerProperties bladeLoadBalancerProperties) {
		String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
		return new GrayscaleLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), bladeLoadBalancerProperties);
	}

	@Bean
	public LoadBalancerClientSpecification loadBalancerClientSpecification() {
		return new LoadBalancerClientSpecification("default.bladeLoadBalancerConfiguration",
			new Class[]{JLoadBalancerConfiguration.class});
	}

}

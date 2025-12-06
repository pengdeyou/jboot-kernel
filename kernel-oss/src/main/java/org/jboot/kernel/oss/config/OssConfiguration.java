
package org.jboot.kernel.oss.config;

import org.jboot.kernel.oss.props.OssProperties;
import org.jboot.kernel.oss.rule.JOssRule;
import org.jboot.kernel.oss.rule.OssRule;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * OSS对象存储配置类
 * 用于配置通用的OSS规则和属性
 *
 * @author Chill
 */
@AutoConfiguration
@EnableConfigurationProperties(OssProperties.class)
public class OssConfiguration {

	/**
	 * 配置默认的OSS规则实现
	 * 当容器中不存在 OssRule 类型的Bean时生效
	 *
	 * @return OssRule OSS规则实现类
	 */
	@Bean
	@ConditionalOnMissingBean(OssRule.class)
	public OssRule ossRule() {
		return new JOssRule();
	}

}


package org.jboot.kernel.rds.config;

import org.jbatis.rds.autoconfigure.JbatisProperties.CoreConfiguration;
import org.jbatis.rds.autoconfigure.JbatisPropertiesCustomizer;
import org.jbatis.rds.extension.plugins.JbatisPlusInterceptor;
import org.jbatis.rds.extension.plugins.handler.TenantLineHandler;
import org.jbatis.rds.extension.plugins.inner.TenantLineInnerInterceptor;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.nologging.NoLoggingImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.jboot.kernel.rds.intercept.QueryInterceptor;
import org.jboot.kernel.rds.plugins.JPaginationInterceptor;
import org.jboot.kernel.rds.plugins.SqlLogInterceptor;
import org.jboot.kernel.rds.props.JbatisProperties;
import org.jboot.kernel.secure.utils.SecureUtil;
import org.jboot.kernel.toolkit.constant.JConstant;
import org.jboot.kernel.toolkit.utils.Func;
import org.jboot.kernel.toolkit.utils.ObjectUtil;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

/**
 * mybatis plus 配置
 *
 * @author Corsak
 */
@AutoConfiguration
@AllArgsConstructor
@MapperScan("org.jboot.**.mapper.**")
@EnableConfigurationProperties(JbatisProperties.class)
public class JbatisConfiguration {

	/**
	 * 租户拦截器
	 */
	@Bean
	@ConditionalOnMissingBean(TenantLineInnerInterceptor.class)
	public TenantLineInnerInterceptor tenantLineInnerInterceptor() {
		return new TenantLineInnerInterceptor(new TenantLineHandler() {
			@Override
			public Expression getTenantId() {
				return new StringValue(Func.toStr(SecureUtil.getTenantId(), JConstant.ADMIN_TENANT_ID));
			}

			@Override
			public boolean ignoreTable(String tableName) {
				return true;
			}
		});
	}

	/**
	 * mybatis-plus 拦截器集合
	 */
	@Bean
	@ConditionalOnMissingBean(JbatisPlusInterceptor.class)
	public JbatisPlusInterceptor mybatisPlusInterceptor(ObjectProvider<QueryInterceptor[]> queryInterceptors,
														 TenantLineInnerInterceptor tenantLineInnerInterceptor,
														 JbatisProperties mybatisPlusProperties) {
		JbatisPlusInterceptor interceptor = new JbatisPlusInterceptor();
		// 配置租户拦截器
		interceptor.addInnerInterceptor(tenantLineInnerInterceptor);
		// 配置分页拦截器
		JPaginationInterceptor paginationInterceptor = new JPaginationInterceptor();
		// 配置自定义查询拦截器
		QueryInterceptor[] queryInterceptorArray = queryInterceptors.getIfAvailable();
		if (ObjectUtil.isNotEmpty(queryInterceptorArray)) {
			AnnotationAwareOrderComparator.sort(queryInterceptorArray);
			paginationInterceptor.setQueryInterceptors(queryInterceptorArray);
		}
		paginationInterceptor.setMaxLimit(mybatisPlusProperties.getPageLimit());
		paginationInterceptor.setOverflow(mybatisPlusProperties.getOverflow());
		interceptor.addInnerInterceptor(paginationInterceptor);
		return interceptor;
	}

	/**
	 * sql 日志
	 *
	 * @return SqlLogInterceptor
	 */
	@Bean
	@ConditionalOnProperty(value = "jboot.kernel.mybatis-plus.sql-log", matchIfMissing = true)
	public SqlLogInterceptor sqlLogInterceptor(JbatisProperties properties) {
		return new SqlLogInterceptor(properties);
	}

	/**
	 * 关闭 mybatis 默认日志
	 */
	@Bean
	public JbatisPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
		return properties -> {
			CoreConfiguration configuration = properties.getConfiguration();
			if (configuration != null) {
				Class<? extends Log> logImpl = configuration.getLogImpl();
				if (logImpl == null) {
					configuration.setLogImpl(NoLoggingImpl.class);
				}
			}
		};
	}

}


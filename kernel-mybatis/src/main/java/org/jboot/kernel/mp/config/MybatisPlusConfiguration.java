
package org.jboot.kernel.mp.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties.CoreConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.nologging.NoLoggingImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.jboot.kernel.mp.intercept.QueryInterceptor;
import org.jboot.kernel.mp.plugins.JPaginationInterceptor;
import org.jboot.kernel.mp.plugins.SqlLogInterceptor;
import org.jboot.kernel.mp.props.MybatisPlusProperties;
import org.jboot.kernel.secure.utils.SecureUtil;
import org.jboot.kernel.tool.constant.JConstant;
import org.jboot.kernel.tool.utils.Func;
import org.jboot.kernel.tool.utils.ObjectUtil;
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
@EnableConfigurationProperties(MybatisPlusProperties.class)
public class MybatisPlusConfiguration {

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
	@ConditionalOnMissingBean(MybatisPlusInterceptor.class)
	public MybatisPlusInterceptor mybatisPlusInterceptor(ObjectProvider<QueryInterceptor[]> queryInterceptors,
														 TenantLineInnerInterceptor tenantLineInnerInterceptor,
														 MybatisPlusProperties mybatisPlusProperties) {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
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
	@ConditionalOnProperty(value = "blade.mybatis-plus.sql-log", matchIfMissing = true)
	public SqlLogInterceptor sqlLogInterceptor(MybatisPlusProperties properties) {
		return new SqlLogInterceptor(properties);
	}

	/**
	 * 关闭 mybatis 默认日志
	 */
	@Bean
	public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
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


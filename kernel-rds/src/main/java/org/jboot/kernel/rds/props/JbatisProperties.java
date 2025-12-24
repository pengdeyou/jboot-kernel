
package org.jboot.kernel.rds.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MybatisPlus配置类
 *
 * @author Corsak
 */
@Data
@ConfigurationProperties(prefix = "jboot.kernel.jbatis")
public class JbatisProperties {

	/**
	 * 分页最大数
	 */
	private Long pageLimit = 500L;

	/**
	 * 溢出总页数后是否进行处理
	 */
	protected Boolean overflow = false;

	/**
	 * 是否打印 sql
	 */
	private boolean sqlLog = true;

}

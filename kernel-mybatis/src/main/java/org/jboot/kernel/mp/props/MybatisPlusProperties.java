
package org.jboot.kernel.mp.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MybatisPlus配置类
 *
 * @author Corsak
 */
@Data
@ConfigurationProperties(prefix = "jboot.kernel.mybatis-plus")
public class MybatisPlusProperties {

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

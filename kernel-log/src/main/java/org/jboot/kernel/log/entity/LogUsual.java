
package org.jboot.kernel.log.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author Chill
 * @since 2018-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_log_usual")
public class LogUsual extends LogAbstract implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 日志级别
	 */
	private String logLevel;
	/**
	 * 日志业务id
	 */
	private String logId;
	/**
	 * 日志数据
	 */
	private String logData;


}

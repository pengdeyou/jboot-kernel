
package org.jboot.kernel.log.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 服务 异常
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_log_error")
public class LogError extends LogAbstract implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 堆栈信息
	 */
	private String stackTrace;
	/**
	 * 异常名
	 */
	private String exceptionName;
	/**
	 * 异常消息
	 */
	private String message;

	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 代码行数
	 */
	private Integer lineNumber;


}


package org.jboot.kernel.toolkit.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 校验规则类
 *
 * @author Corsak
 */
@Data
@AllArgsConstructor
public class ValidationRule implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	/**
	 * 校验值
	 */
	private String value;
	/**
	 * 校验正则
	 */
	private String regex;
	/**
	 * 校验信息提示
	 */
	private String message;
}

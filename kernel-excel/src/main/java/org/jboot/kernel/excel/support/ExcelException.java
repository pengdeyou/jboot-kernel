
package org.jboot.kernel.excel.support;

import java.io.Serial;

/**
 * Excel异常处理类
 *
 * @author Corsak
 */
public class ExcelException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 默认构造函数
	 * @param message 异常信息
	 */
	public ExcelException(String message) {
		super(message);
	}
}

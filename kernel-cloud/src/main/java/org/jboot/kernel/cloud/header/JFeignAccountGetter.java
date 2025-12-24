package org.jboot.kernel.cloud.header;
import org.springframework.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
public interface JFeignAccountGetter {
	/**
	 * 账号信息获取器
	 *
	 * @param request HttpServletRequest
	 * @return account 信息
	 */
	@Nullable
	String get(HttpServletRequest request);
}

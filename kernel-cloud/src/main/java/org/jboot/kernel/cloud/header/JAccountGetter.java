
package org.jboot.kernel.cloud.header;

import org.jboot.kernel.secure.JUser;
import org.jboot.kernel.secure.utils.SecureUtil;
import org.jboot.kernel.tool.utils.Charsets;
import org.jboot.kernel.tool.utils.UrlUtil;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 用户信息获取器
 *
 * @author Chill
 */
public class JAccountGetter implements JFeignAccountGetter {

	@Override
	public String get(HttpServletRequest request) {
		JUser account = SecureUtil.getUser();
		if (account == null) {
			return null;
		}
		// 增加用户头, 123[admin]
		String xAccount = String.format("%s[%s]", account.getUserId(), account.getUserName());
		return UrlUtil.encodeURL(xAccount, Charsets.UTF_8);
	}

}

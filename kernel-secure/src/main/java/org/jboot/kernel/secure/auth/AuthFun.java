
package org.jboot.kernel.secure.auth;

import org.jboot.kernel.launch.constant.TokenConstant;
import org.jboot.kernel.secure.utils.SecureUtil;
import org.jboot.kernel.tool.constant.RoleConstant;
import org.jboot.kernel.tool.utils.CollectionUtil;
import org.jboot.kernel.tool.utils.Func;
import org.jboot.kernel.tool.utils.StringUtil;
import org.jboot.kernel.tool.utils.WebUtil;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 权限判断
 *
 * @author Corsak
 */
public class AuthFun {

	/**
	 * 放行所有请求
	 *
	 * @return {boolean}
	 */
	public boolean permitAll() {
		return true;
	}

	/**
	 * 只有超管角色才可访问
	 *
	 * @return {boolean}
	 */
	public boolean denyAll() {
		return hasRole(RoleConstant.ADMIN);
	}

	/**
	 * 判断是否有该角色权限
	 *
	 * @param role 单角色
	 * @return {boolean}
	 */
	public boolean hasRole(String role) {
		return hasAnyRole(role);
	}

	/**
	 * 判断是否有该角色权限
	 *
	 * @param role 角色集合
	 * @return {boolean}
	 */
	public boolean hasAnyRole(String... role) {
		String userRole = SecureUtil.getUser().getRoleName();
		if (StringUtil.isBlank(userRole)) {
			return false;
		}
		String[] roles = Func.toStrArray(userRole);
		for (String r : role) {
			if (CollectionUtil.contains(roles, r)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断请求是否为加密token
	 *
	 * @return {boolean}
	 */
	public boolean hasCrypto() {
		HttpServletRequest request = WebUtil.getRequest();
		String auth = Objects.requireNonNull(request).getHeader(TokenConstant.HEADER);
		return SecureUtil.isCrypto(
			StringUtil.isNotBlank(auth) ? auth : request.getParameter(TokenConstant.HEADER)
		);
	}

}

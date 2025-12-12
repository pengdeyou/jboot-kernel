
package org.jboot.kernel.datascope.handler;

import org.jboot.kernel.datascope.model.DataScopeModel;
import org.jboot.kernel.secure.AuthUser;

/**
 * 数据权限规则
 *
 * @author Corsak
 */
public interface DataScopeHandler {

	/**
	 * 获取过滤sql
	 *
	 * @param mapperId    数据查询类
	 * @param dataScope   数据权限类
	 * @param bladeUser   当前用户信息
	 * @param originalSql 原始Sql
	 * @return sql
	 */
	String sqlCondition(String mapperId, DataScopeModel dataScope, AuthUser bladeUser, String originalSql);

}

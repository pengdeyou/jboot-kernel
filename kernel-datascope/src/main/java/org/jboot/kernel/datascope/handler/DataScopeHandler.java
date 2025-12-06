
package org.jboot.kernel.datascope.handler;

import org.jboot.kernel.datascope.model.DataScopeModel;
import org.jboot.kernel.secure.BladeUser;

/**
 * 数据权限规则
 *
 * @author Chill
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
	String sqlCondition(String mapperId, DataScopeModel dataScope, BladeUser bladeUser, String originalSql);

}

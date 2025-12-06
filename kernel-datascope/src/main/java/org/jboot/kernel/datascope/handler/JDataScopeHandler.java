
package org.jboot.kernel.datascope.handler;

import lombok.RequiredArgsConstructor;
import org.jboot.kernel.datascope.enums.DataScopeEnum;
import org.jboot.kernel.datascope.model.DataScopeModel;
import org.jboot.kernel.secure.JUser;
import org.jboot.kernel.tool.constant.RoleConstant;
import org.jboot.kernel.tool.utils.BeanUtil;
import org.jboot.kernel.tool.utils.Func;
import org.jboot.kernel.tool.utils.PlaceholderUtil;
import org.jboot.kernel.tool.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 默认数据权限规则
 *
 * @author Chill
 */
@RequiredArgsConstructor
public class JDataScopeHandler implements DataScopeHandler {

	private final ScopeModelHandler scopeModelHandler;

	@Override
	public String sqlCondition(String mapperId, DataScopeModel dataScope, JUser bladeUser, String originalSql) {

		//数据权限资源编号
		String code = dataScope.getResourceCode();

		//根据mapperId从数据库中获取对应模型
		DataScopeModel dataScopeDb = scopeModelHandler.getDataScopeByMapper(mapperId, bladeUser.getRoleId());

		//mapperId配置未取到则从数据库中根据资源编号获取
		if (dataScopeDb == null && StringUtil.isNotBlank(code)) {
			dataScopeDb = scopeModelHandler.getDataScopeByCode(code);
		}

		//未从数据库找到对应配置则采用默认
		dataScope = (dataScopeDb != null) ? dataScopeDb : dataScope;

		//判断数据权限类型并组装对应Sql
		Integer scopeRule = Objects.requireNonNull(dataScope).getScopeType();
		DataScopeEnum scopeTypeEnum = DataScopeEnum.of(scopeRule);
		List<Long> ids = new ArrayList<>();
		String whereSql = "where scope.{} in ({})";
		if (DataScopeEnum.ALL == scopeTypeEnum || StringUtil.containsAny(bladeUser.getRoleName(), RoleConstant.ADMIN)) {
			return null;
		} else if (DataScopeEnum.CUSTOM == scopeTypeEnum) {
			whereSql = PlaceholderUtil.getDefaultResolver().resolveByMap(dataScope.getScopeValue(), BeanUtil.toMap(bladeUser));
		} else if (DataScopeEnum.OWN == scopeTypeEnum) {
			ids.add(bladeUser.getUserId());
		} else if (DataScopeEnum.OWN_DEPT == scopeTypeEnum) {
			ids.addAll(Func.toLongList(bladeUser.getDeptId()));
		} else if (DataScopeEnum.OWN_DEPT_CHILD == scopeTypeEnum) {
			List<Long> deptIds = Func.toLongList(bladeUser.getDeptId());
			ids.addAll(deptIds);
			deptIds.forEach(deptId -> {
				List<Long> deptIdList = scopeModelHandler.getDeptAncestors(deptId);
				ids.addAll(deptIdList);
			});
		}
		return StringUtil.format("select {} from ({}) scope " + whereSql, Func.toStr(dataScope.getScopeField(), "*"), originalSql, dataScope.getScopeColumn(), StringUtil.join(ids));
	}

}

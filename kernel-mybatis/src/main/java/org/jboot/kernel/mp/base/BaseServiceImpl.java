
package org.jboot.kernel.mp.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.validation.constraints.NotEmpty;
import lombok.SneakyThrows;
import org.jboot.kernel.mp.support.Condition;
import org.jboot.kernel.secure.JUser;
import org.jboot.kernel.secure.utils.SecureUtil;
import org.jboot.kernel.tool.constant.JConstant;
import org.jboot.kernel.tool.utils.DateUtil;
import org.jboot.kernel.tool.utils.Func;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 业务封装基础类
 *
 * @param <M> mapper
 * @param <T> model
 * @author Chill
 */
@Validated
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

	@Override
	public T queryOne(QueryWrapper<T> queryWrapper) {
		Page<T> page = Page.of(1, 1);
		Page<T> result = super.page(page, queryWrapper);
		return result.getRecords().isEmpty() ? null : result.getRecords().get(0);
	}

	@Override
	public T queryDetail(T entity) {
		QueryWrapper<T> queryWrapper = Condition.getQueryWrapper(entity);
		return queryWrapper.isEmptyOfWhere() ? null : super.getOne(queryWrapper);
	}

	@Override
	public T queryDetail(Map<String, Object> entity) {
		QueryWrapper<T> queryWrapper = Condition.getQueryWrapper(entity, getEntityClass());
		return queryWrapper.isEmptyOfWhere() ? null : super.getOne(queryWrapper);
	}

	@Override
	public boolean save(T entity) {
		this.resolveSave(entity);
		return super.save(entity);
	}

	@Override
	public boolean saveBatch(Collection<T> entityList, int batchSize) {
		entityList.forEach(this::resolveSave);
		return super.saveBatch(entityList, batchSize);
	}

	@Override
	public boolean updateById(T entity) {
		this.resolveUpdate(entity);
		return super.updateById(entity);
	}

	@Override
	public boolean updateBatchById(Collection<T> entityList, int batchSize) {
		entityList.forEach(this::resolveUpdate);
		return super.updateBatchById(entityList, batchSize);
	}

	@Override
	public boolean saveOrUpdate(T entity) {
		if (entity.getId() == null) {
			return this.save(entity);
		} else {
			return this.updateById(entity);
		}
	}

	@Override
	public boolean deleteLogic(@NotEmpty List<Long> ids) {
		return super.removeByIds(ids);
	}

	@Override
	public boolean isFieldDuplicate(SFunction<T, ?> field, Object value, Long excludedId) {
		LambdaQueryWrapper<T> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(field, value);
		if (excludedId != null) {
			queryWrapper.ne(T::getId, excludedId);
		}
		return baseMapper.selectCount(queryWrapper) > 0;
	}

	@SneakyThrows
	private void resolveSave(T entity) {
		JUser user = SecureUtil.getUser();
		if (user != null) {
			entity.setCreateUser(user.getUserId());
			entity.setCreateDept(Func.firstLong(user.getDeptId()));
			entity.setUpdateUser(user.getUserId());
		}
		Date now = DateUtil.now();
		entity.setCreateTime(now);
		entity.setUpdateTime(now);
		if (entity.getStatus() == null) {
			entity.setStatus(JConstant.DB_STATUS_NORMAL);
		}
		entity.setIsDeleted(JConstant.DB_NOT_DELETED);
	}

	@SneakyThrows
	private void resolveUpdate(T entity) {
		JUser user = SecureUtil.getUser();
		if (user != null) {
			entity.setUpdateUser(user.getUserId());
		}
		entity.setUpdateTime(DateUtil.now());
	}

}

package org.jboot.kernel.rds;

import org.jbatis.rds.kernel.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

/**
 * mybatisplus自定义填充
 *
 * @author Corsak
 */
@Slf4j
public class JMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {

	}

	@Override
	public void updateFill(MetaObject metaObject) {

	}

}

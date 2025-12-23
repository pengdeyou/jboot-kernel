
package org.jboot.kernel.toolkit.node;

import java.io.Serializable;
import java.util.List;

/**
 * Created by J.
 *
 * @author smallchill
 */
public interface INode<T> extends Serializable {

	/**
	 * 主键
	 *
	 * @return Long
	 */
	Long getId();

	/**
	 * 父主键
	 *
	 * @return Long
	 */
	Long getParentId();

	/**
	 * 子孙节点
	 *
	 * @return List
	 */
	List<T> getChildren();

	/**
	 * 是否有子孙节点
	 *
	 * @return Boolean
	 */
	default Boolean getHasChildren() {
		return false;
	}

}

package com.fembase.modules.signin.dao;

import java.util.List;

import com.fembase.common.persistence.annotation.MyBatisDao;
import com.fembase.modules.signin.entity.SignIn;

/**
 * signDAO接口
 * @version 2015-09-19
 */
@MyBatisDao
public interface SignInDao {
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public SignIn get(String id);
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public SignIn get(SignIn entity);
	
	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<SignIn>());
	 * @param entity
	 * @return
	 */
	public List<SignIn> findList(SignIn entity);
	
	/**
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	public List<SignIn> findAllList(SignIn entity);
	
	/**
	 * 查询所有数据列表
	 * @see public List<SignIn> findAllList(SignIn entity)
	 * @return
	 */
	@Deprecated
	public List<SignIn> findAllList();
	
	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	public int insert(SignIn entity);
	
	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	public int update(SignIn entity);
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param id
	 * @see public int delete(SignIn entity)
	 * @return
	 */
	@Deprecated
	public int delete(String id);
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param entity
	 * @return
	 */
	public int delete(SignIn entity);
	
}
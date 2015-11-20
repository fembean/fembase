package com.fembase.modules.user.dao;

import org.apache.ibatis.annotations.Param;

import com.fembase.common.persistence.CrudDao;
import com.fembase.common.persistence.annotation.MyBatisDao;
import com.fembase.modules.user.entity.User;

/**
 * 用户模块DAO接口
 * @version 2015-07-20
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	User getUserByPhoneOrEmail(@Param("phoneOrEmail")String phoneOrEmail);
	
}
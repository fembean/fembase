package com.fembase.modules.integral.dao;

import com.fembase.common.persistence.CrudDao;
import com.fembase.common.persistence.annotation.MyBatisDao;
import com.fembase.modules.integral.entity.UserIntegralLog;

/**
 * 用户积分记录DAO接口
 * @version 2015-09-19
 */
@MyBatisDao
public interface UserIntegralLogDao extends CrudDao<UserIntegralLog> {
	
}
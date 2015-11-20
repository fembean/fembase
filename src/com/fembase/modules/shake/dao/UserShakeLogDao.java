package com.fembase.modules.shake.dao;

import com.fembase.common.persistence.CrudDao;
import com.fembase.common.persistence.annotation.MyBatisDao;
import com.fembase.modules.shake.entity.UserShakeLog;

/**
 * 用户摇奖记录DAO接口
 * @version 2015-09-19
 */
@MyBatisDao
public interface UserShakeLogDao extends CrudDao<UserShakeLog> {
	
}
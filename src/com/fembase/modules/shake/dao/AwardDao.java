package com.fembase.modules.shake.dao;

import com.fembase.common.persistence.CrudDao;
import com.fembase.common.persistence.annotation.MyBatisDao;
import com.fembase.modules.shake.entity.Award;

/**
 * 奖品DAO接口
 * @version 2015-09-19
 */
@MyBatisDao
public interface AwardDao extends CrudDao<Award> {
	
}
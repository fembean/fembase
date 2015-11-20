package com.fembase.modules.poto.dao;

import com.fembase.common.persistence.CrudDao;
import com.fembase.common.persistence.annotation.MyBatisDao;
import com.fembase.modules.poto.entity.Poto;

/**
 * potoDAO接口
 * @version 2015-07-26
 */
@MyBatisDao
public interface PotoDao extends CrudDao<Poto> {
	
}
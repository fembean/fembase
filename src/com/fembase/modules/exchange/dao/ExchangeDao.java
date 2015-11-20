package com.fembase.modules.exchange.dao;

import com.fembase.common.persistence.CrudDao;
import com.fembase.common.persistence.annotation.MyBatisDao;
import com.fembase.modules.exchange.entity.Exchange;

/**
 * 兑换DAO接口
 * @version 2015-09-25
 */
@MyBatisDao
public interface ExchangeDao extends CrudDao<Exchange> {
	
}
/**
 */
package com.fembase.modules.exchange.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fembase.common.persistence.Page;
import com.fembase.common.service.CrudService;
import com.fembase.modules.exchange.dao.ExchangeDao;
import com.fembase.modules.exchange.entity.Exchange;

/**
 * 兑换Service
 * @author dong
 * @version 2015-09-25
 */
@Service
@Transactional(readOnly = true)
public class ExchangeService extends CrudService<ExchangeDao, Exchange> {

	public Exchange get(String id) {
		return super.get(id);
	}
	
	public List<Exchange> findList(Exchange exchange) {
		return super.findList(exchange);
	}
	
	public Page<Exchange> findPage(Page<Exchange> page, Exchange exchange) {
		return super.findPage(page, exchange);
	}
	
	@Transactional(readOnly = false)
	public void save(Exchange exchange) {
		super.save(exchange);
	}
	
	@Transactional(readOnly = false)
	public void delete(Exchange exchange) {
		super.delete(exchange);
	}
	
}
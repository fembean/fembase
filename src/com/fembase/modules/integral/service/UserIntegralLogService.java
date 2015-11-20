/**
 */
package com.fembase.modules.integral.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fembase.common.persistence.Page;
import com.fembase.common.service.CrudService;
import com.fembase.modules.integral.dao.UserIntegralLogDao;
import com.fembase.modules.integral.entity.UserIntegralLog;

/**
 * 用户积分记录Service
 * @author dong
 * @version 2015-09-19
 */
@Service
@Transactional(readOnly = true)
public class UserIntegralLogService extends CrudService<UserIntegralLogDao, UserIntegralLog> {

	public UserIntegralLog get(String id) {
		return super.get(id);
	}
	
	public List<UserIntegralLog> findList(UserIntegralLog userIntegralLog) {
		return super.findList(userIntegralLog);
	}
	
	public Page<UserIntegralLog> findPage(Page<UserIntegralLog> page, UserIntegralLog userIntegralLog) {
		return super.findPage(page, userIntegralLog);
	}
	
	@Transactional(readOnly = false)
	public void save(UserIntegralLog userIntegralLog) {
		super.save(userIntegralLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserIntegralLog userIntegralLog) {
		super.delete(userIntegralLog);
	}
	
}
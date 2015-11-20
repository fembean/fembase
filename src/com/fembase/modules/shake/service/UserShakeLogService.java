/**
 */
package com.fembase.modules.shake.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fembase.common.persistence.Page;
import com.fembase.common.service.CrudService;
import com.fembase.modules.shake.dao.UserShakeLogDao;
import com.fembase.modules.shake.entity.UserShakeLog;

/**
 * 用户摇奖记录Service
 * @author dong
 * @version 2015-09-19
 */
@Service
@Transactional(readOnly = true)
public class UserShakeLogService extends CrudService<UserShakeLogDao, UserShakeLog> {

	public UserShakeLog get(String id) {
		return super.get(id);
	}
	
	public List<UserShakeLog> findList(UserShakeLog userShakeLog) {
		return super.findList(userShakeLog);
	}
	
	public Page<UserShakeLog> findPage(Page<UserShakeLog> page, UserShakeLog userShakeLog) {
		return super.findPage(page, userShakeLog);
	}
	
	@Transactional(readOnly = false)
	public void save(UserShakeLog userShakeLog) {
		super.save(userShakeLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserShakeLog userShakeLog) {
		super.delete(userShakeLog);
	}
	
}
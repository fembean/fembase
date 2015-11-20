/**
 */
package com.fembase.modules.shake.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fembase.common.persistence.Page;
import com.fembase.common.service.CrudService;
import com.fembase.modules.shake.dao.AwardDao;
import com.fembase.modules.shake.entity.Award;

/**
 * 奖品Service
 * @author dong
 * @version 2015-09-19
 */
@Service
@Transactional(readOnly = true)
public class AwardService extends CrudService<AwardDao, Award> {

	public Award get(String id) {
		return super.get(id);
	}
	
	public List<Award> findList(Award award) {
		return super.findList(award);
	}
	
	public Page<Award> findPage(Page<Award> page, Award award) {
		return super.findPage(page, award);
	}
	
	@Transactional(readOnly = false)
	public void save(Award award) {
		super.save(award);
	}
	
	@Transactional(readOnly = false)
	public void delete(Award award) {
		super.delete(award);
	}
	
}
/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.common.entity.User;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.sys.service.OfficeService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.sys.dao.OfficeDao;
import com.thinkgem.jeesite.common.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeServiceImpl extends TreeService<OfficeDao, Office> implements OfficeService {

	@Cacheable(value = "officeList")
	public List<Office> findAll(){
		return dao.findAllList(new Office());
	}

	@Cacheable(value = "officeList" , key = "#user.id")
	@Override
	public List<Office> findByUser(User user) {
		if(user.isAdmin()){
			return findAll();
		} else {
			Office office = new Office();
			office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
			return dao.findList(office);
		}
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return findAll();
		}else{
			return findByUser(UserUtils.getUser());
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null){
			office.setParentIds(office.getParentIds()+"%");
			return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}

	@CacheEvict(value = "officeList",allEntries = true)
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
	}

	@CacheEvict(value = "officeList",allEntries = true)
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
	}
	
}

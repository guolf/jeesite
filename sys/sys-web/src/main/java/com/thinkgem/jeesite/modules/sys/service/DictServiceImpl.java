/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.sys.service.DictService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.sys.dao.DictDao;
import com.thinkgem.jeesite.common.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 字典Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictServiceImpl extends CrudService<DictDao, Dict> implements DictService {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Override
	public List<Dict> findAllList(Dict dict) {
		return dao.findAllList(dict);
	}

	@Cacheable(value = "dictMap")
	@Override
	public Map<String, List<Dict>> getDictMap() {
		Map<String, List<Dict>> dictMap = new HashMap<String, List<Dict>>();
		List<Dict> dicts = findAllList(new Dict());
		for (Dict dict : dicts) {
			List<Dict> dictList = dictMap.get(dict.getType());
			if (dictList == null){
				dictMap.put(dict.getType(), Lists.newArrayList(dict));
			}
		}
		return dictMap;
	}
}

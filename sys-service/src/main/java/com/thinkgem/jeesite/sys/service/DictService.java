package com.thinkgem.jeesite.sys.service;

import com.thinkgem.jeesite.base.CrudService;
import com.thinkgem.jeesite.common.entity.Dict;
import com.thinkgem.jeesite.sys.dao.DictDao;

import java.util.List;

/**
 * Created by guolf on 17/8/24.
 */
public interface DictService extends CrudService<DictDao, Dict> {

    public List<String> findTypeList();
}

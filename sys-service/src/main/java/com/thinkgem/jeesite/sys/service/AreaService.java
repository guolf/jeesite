package com.thinkgem.jeesite.sys.service;

import com.thinkgem.jeesite.base.TreeService;
import com.thinkgem.jeesite.common.entity.Area;
import com.thinkgem.jeesite.sys.dao.AreaDao;

import java.util.List;

/**
 * Created by guolf on 17/8/23.
 */
public interface AreaService extends TreeService<AreaDao, Area> {

    public List<Area> findAll();
}

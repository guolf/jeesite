package com.thinkgem.jeesite.sys.service;

import com.thinkgem.jeesite.base.TreeService;
import com.thinkgem.jeesite.common.entity.Office;
import com.thinkgem.jeesite.sys.dao.OfficeDao;

import java.util.List;

/**
 * Created by guolf on 17/8/24.
 */
public interface OfficeService  extends TreeService<OfficeDao, Office> {

    public List<Office> findAll();

    public List<Office> findList(Boolean isAll);
}

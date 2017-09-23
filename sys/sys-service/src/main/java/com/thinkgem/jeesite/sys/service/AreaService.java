package com.thinkgem.jeesite.sys.service;

import com.thinkgem.jeesite.common.entity.Area;

import java.util.List;

/**
 * Created by guolf on 17/8/23.
 */
public interface AreaService extends BaseService<Area> {

    List<Area> findAll();
}

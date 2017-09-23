/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import com.thinkgem.jeesite.common.entity.Area;
import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.sys.dao.AreaDao;
import com.thinkgem.jeesite.sys.service.AreaService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 区域Service
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaServiceImpl extends TreeService<AreaDao, Area> implements AreaService {

    @Cacheable("areaList")
    @Override
    public List<Area> findAll() {
        return dao.findAllList(new Area());
    }

    @CacheEvict( value =  "areaList",allEntries = true)
    @Transactional(readOnly = false)
    public void save(Area area) {
        super.save(area);
    }

    @CacheEvict( value =  "areaList",allEntries = true)
    @Transactional(readOnly = false)
    public void delete(Area area) {
        super.delete(area);
        UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
    }

}

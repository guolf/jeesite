package com.thinkgem.jeesite.sys.service;

import com.thinkgem.jeesite.base.CrudService;
import com.thinkgem.jeesite.common.entity.Menu;
import com.thinkgem.jeesite.sys.dao.MenuDao;

import java.util.List;

/**
 * Created by guolf on 17/8/24.
 */
public interface MenuService extends CrudService<MenuDao,Menu> {


    public List<Menu> findByParentIdsLike(Menu menu);

    public void updateParentIds(Menu menu);

    public void updateSort(Menu menu);
}

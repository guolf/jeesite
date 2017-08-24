package com.thinkgem.jeesite.modules.sys.service;

import com.thinkgem.jeesite.common.entity.Menu;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.sys.dao.MenuDao;
import com.thinkgem.jeesite.sys.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by guolf on 17/8/22.
 */
@Service
public class MenuServiceImpl extends CrudService<MenuDao,Menu> implements MenuService {

    public List<Menu> findByParentIdsLike(Menu menu){
        return dao.findByParentIdsLike(menu);
    }

    public void updateParentIds(Menu menu){
        dao.updateParentIds(menu);
    }

    public void updateSort(Menu menu){
        dao.updateSort(menu);
    }
}

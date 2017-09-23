package com.thinkgem.jeesite.sys.service;

import com.thinkgem.jeesite.common.entity.Menu;
import com.thinkgem.jeesite.common.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by guolf on 17/8/24.
 */
public interface MenuService extends BaseService<Menu> {

    List<Menu> findByParentIdsLike(Menu menu);

    void updateParentIds(Menu menu);

    void updateSort(Menu menu);

    List<Menu> findByUserId(Menu menu);

    List<Menu> findByUser(User user);

    Map<String, String> getMenuMap();

}

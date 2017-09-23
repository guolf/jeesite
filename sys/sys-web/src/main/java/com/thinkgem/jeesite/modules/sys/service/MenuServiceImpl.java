package com.thinkgem.jeesite.modules.sys.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.entity.Menu;
import com.thinkgem.jeesite.common.entity.User;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.sys.dao.MenuDao;
import com.thinkgem.jeesite.sys.service.MenuService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Cacheable(value = "menuList",key="#menu.userId")
    @Override
    public List<Menu> findByUserId(Menu menu) {
        return dao.findByUserId(menu);
    }

    /**
     * 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
     *
     * @return
     */
    @Cacheable(value = "menuNamePathMap")
    @Override
    public Map<String, String> getMenuMap() {
        Map<String, String> menuMap = Maps.newHashMap();
        List<Menu> menuList = dao.findAllList(new Menu());
        for (Menu menu : menuList) {
            String namePath = "";
            if (menu.getParentIds() != null) {
                List<String> namePathList = Lists.newArrayList();
                for (String id : StringUtils.split(menu.getParentIds(), ",")) {
                    if (Menu.getRootId().equals(id)) {
                        continue; // 过滤跟节点
                    }
                    for (Menu m : menuList) {
                        if (m.getId().equals(id)) {
                            namePathList.add(m.getName());
                            break;
                        }
                    }
                }
                namePathList.add(menu.getName());
                namePath = StringUtils.join(namePathList, "-");
            }
            // 设置菜单名称路径
            if (StringUtils.isNotBlank(menu.getHref())) {
                menuMap.put(menu.getHref(), namePath);
            } else if (StringUtils.isNotBlank(menu.getPermission())) {
                for (String p : StringUtils.split(menu.getPermission())) {
                    menuMap.put(p, namePath);
                }
            }
        }
        return menuMap;
    }


    /**
     * 获取用户授权菜单
     * @param user
     * @return
     */
    @Cacheable(value = "menuList",key = "#user.id")
    @Override
    public List<Menu> findByUser(User user) {
        if(user.isAdmin()){
            return dao.findAllList(new Menu());
        }else{
            Menu m = new Menu();
            m.setUserId(user.getId());
            return findByUserId(m);
        }
    }


}

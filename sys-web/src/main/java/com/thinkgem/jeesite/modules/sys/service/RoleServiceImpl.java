package com.thinkgem.jeesite.modules.sys.service;

import com.thinkgem.jeesite.common.entity.Role;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.sys.dao.RoleDao;
import com.thinkgem.jeesite.sys.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * Created by guolf on 17/8/22.
 */
@Service
public class RoleServiceImpl extends CrudService<RoleDao,Role> implements RoleService {

    public void deleteRoleMenu(Role role){
        dao.deleteRoleMenu(role);
    }

    public Role getByName(Role role){
        return dao.getByName(role);
    }

    public Role getByEnname(Role role){
        return dao.getByEnname(role);
    }

    public void insertRoleMenu(Role role){
        dao.insertRoleMenu(role);
    }

    public void deleteRoleOffice(Role role){
        dao.deleteRoleMenu(role);
    }

    public void insertRoleOffice(Role role){
        dao.insertRoleOffice(role);
    }
}

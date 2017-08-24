package com.thinkgem.jeesite.sys.service;

import com.thinkgem.jeesite.base.CrudService;
import com.thinkgem.jeesite.common.entity.Role;
import com.thinkgem.jeesite.sys.dao.RoleDao;

/**
 * Created by guolf on 17/8/24.
 */
public interface RoleService extends CrudService<RoleDao,Role> {

    public void deleteRoleMenu(Role role);

    public Role getByName(Role role);

    public Role getByEnname(Role role);

    public void insertRoleMenu(Role role);

    public void deleteRoleOffice(Role role);

    public void insertRoleOffice(Role role);
}

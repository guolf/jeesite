package com.thinkgem.jeesite.sys.service;

import com.thinkgem.jeesite.common.entity.Role;
import com.thinkgem.jeesite.common.entity.User;

import java.util.List;

/**
 * Created by guolf on 17/8/24.
 */
public interface RoleService extends BaseService<Role> {

    void deleteRoleMenu(Role role);

    Role getByName(Role role);

    Role getByEnname(Role role);

    void insertRoleMenu(Role role);

    void deleteRoleOffice(Role role);

    void insertRoleOffice(Role role);

    List<Role> getRoleList(User user);


}

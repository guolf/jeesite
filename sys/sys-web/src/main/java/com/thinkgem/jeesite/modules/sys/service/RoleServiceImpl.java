package com.thinkgem.jeesite.modules.sys.service;

import com.thinkgem.jeesite.common.entity.Role;
import com.thinkgem.jeesite.common.entity.User;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.sys.dao.RoleDao;
import com.thinkgem.jeesite.sys.service.RoleService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Cacheable(value = "roleCache",key = "#user.id")
    @Override
    public List<Role> getRoleList(User user) {
        List<Role> roleList ;
        if (user.isAdmin()) {
            roleList = dao.findAllList(new Role());
        } else {
            Role role = new Role();
            role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
            roleList = dao.findList(role);
        }
        return roleList;
    }

    @Cacheable(value = "roleCache")
    @Override
    public List<Role> findList(Role entity) {
        return super.findList(entity);
    }

    @CacheEvict(value = "roleCache")
    @Override
    public void save(Role entity) {
        super.save(entity);
    }

    @CacheEvict(value = "roleCache")
    @Override
    public void delete(Role entity) {
        super.delete(entity);
    }
}

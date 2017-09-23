package com.thinkgem.jeesite.modules.sys.service;

import com.thinkgem.jeesite.common.entity.Office;
import com.thinkgem.jeesite.common.entity.Role;
import com.thinkgem.jeesite.common.entity.User;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.sys.dao.UserDao;
import com.thinkgem.jeesite.sys.service.RoleService;
import com.thinkgem.jeesite.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by guolf on 17/8/22.
 */
@Service
public class UserServiceImpl extends CrudService<UserDao, User> implements UserService {

    @Autowired
    private RoleService roleService;

    @Cacheable(value = "userCache",key = "#loginName")
    public User getByLoginName(String loginName) {
        return getByLoginName(new User(null, loginName));
    }

    @Cacheable(value = "userCache",key = "#user.loginName")
    @Override
    public User getByLoginName(User user) {
        User user1 = dao.getByLoginName(user);
        if(user1 != null) {
            user1.setRoleList(roleService.findList(new Role(user1)));
        }
        return user1;
    }

    @Cacheable(value = "userCache",key = "'role_' + #user.office.id")
    public List<User> findUserByOfficeId(User user) {
        return dao.findUserByOfficeId(user);
    }

    @Cacheable(value = "userCache",key = "#id")
    @Override
    public List<User> findUserByOfficeId(String id) {
        User user = new User();
        user.setOffice(new Office(id));
        return findUserByOfficeId(user);
    }

    public void deleteUserRole(User user) {
        dao.deleteUserRole(user);
    }

    public void insertUserRole(User user) {
        dao.insertUserRole(user);
    }

    public void updateUserInfo(User user) {
        dao.updateUserInfo(user);
    }

    public void updatePasswordById(User user) {
        dao.updatePasswordById(user);
    }

    public void updateLoginInfo(User user){
        dao.updateLoginInfo(user);
    }
}

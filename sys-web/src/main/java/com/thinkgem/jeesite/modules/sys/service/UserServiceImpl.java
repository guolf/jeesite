package com.thinkgem.jeesite.modules.sys.service;

import com.thinkgem.jeesite.common.entity.User;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.sys.dao.UserDao;
import com.thinkgem.jeesite.sys.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by guolf on 17/8/22.
 */
@Service
public class UserServiceImpl extends CrudService<UserDao, User> implements UserService {

    public User getByLoginName(String loginName) {
        return dao.getByLoginName(new User(null, loginName));
    }

    public List<User> findUserByOfficeId(User user) {
        return dao.findUserByOfficeId(user);
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

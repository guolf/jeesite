package com.thinkgem.jeesite.sys.service;

import com.thinkgem.jeesite.base.CrudService;
import com.thinkgem.jeesite.common.entity.User;
import com.thinkgem.jeesite.sys.dao.UserDao;

import java.util.List;

/**
 * Created by guolf on 17/8/24.
 */
public interface UserService extends CrudService<UserDao, User> {

    public User getByLoginName(String loginName);

    public List<User> findUserByOfficeId(User user);

    public void deleteUserRole(User user);

    public void insertUserRole(User user);

    public void updateUserInfo(User user);

    public void updatePasswordById(User user);

    public void updateLoginInfo(User user);
}

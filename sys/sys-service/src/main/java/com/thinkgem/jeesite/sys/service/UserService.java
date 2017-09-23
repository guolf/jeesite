package com.thinkgem.jeesite.sys.service;

import com.thinkgem.jeesite.common.entity.User;

import java.util.List;

/**
 * Created by guolf on 17/8/24.
 */
public interface UserService extends BaseService<User> {

    User getByLoginName(String loginName);

    User getByLoginName(User user);

    List<User> findUserByOfficeId(User user);

    List<User> findUserByOfficeId(String id);

    void deleteUserRole(User user);

    void insertUserRole(User user);

    void updateUserInfo(User user);

    void updatePasswordById(User user);

    void updateLoginInfo(User user);
}

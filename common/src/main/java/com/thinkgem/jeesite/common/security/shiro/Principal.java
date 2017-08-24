package com.thinkgem.jeesite.common.security.shiro;

import com.thinkgem.jeesite.common.entity.User;

import java.io.Serializable;

/**
 * Created by guolf on 17/8/22.
 */
public  class Principal implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; // 编号
    private String loginName; // 登录名
    private String name; // 姓名

    public Principal(User user, boolean mobileLogin) {
        this.id = user.getId();
        this.loginName = user.getLoginName();
        this.name = user.getName();
    }

    public String getId() {
        return id;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id;
    }

}
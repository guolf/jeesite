package com.thinkgem.jeesite.sys.service;

import com.thinkgem.jeesite.common.entity.Office;
import com.thinkgem.jeesite.common.entity.User;

import java.util.List;

/**
 * Created by guolf on 17/8/24.
 */
public interface OfficeService  extends BaseService<Office> {

    List<Office> findAll();

    List<Office> findByUser(User user);

    List<Office> findList(Boolean isAll);
}

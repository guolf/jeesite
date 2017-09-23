package com.thinkgem.jeesite.sys.service;

import com.thinkgem.jeesite.common.persistence.Page;

import java.util.List;

/**
 * Created by guolf on 17/9/23.
 */
public interface BaseService<T> {

    void save(T t);

    T get(String id);

    T get(T entity);

    List<T> findList(T entity);

    Page<T> findPage(Page<T> page, T entity);

    void delete(T entity);
}

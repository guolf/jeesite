package com.thinkgem.jeesite.base;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * Created by guolf on 17/8/23.
 */
public interface TreeService<D extends TreeDao<T>, T extends TreeEntity<T>> extends CrudService<D, T> {

    void save(T entity);

}

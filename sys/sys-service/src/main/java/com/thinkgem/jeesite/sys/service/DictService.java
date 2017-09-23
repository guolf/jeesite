package com.thinkgem.jeesite.sys.service;

import com.thinkgem.jeesite.common.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * Created by guolf on 17/8/24.
 */
public interface DictService extends BaseService<Dict> {

    List<String> findTypeList();

    List<Dict> findAllList(Dict dict);

    Map<String, List<Dict>> getDictMap();
}

package com.thinkgem.jeesite.common.utils.excel;

/**
 * Created by guolf on 17/8/22.
 */
public interface FieldType {

    Object getValue(String... val);

    String setValue(Object val);
}

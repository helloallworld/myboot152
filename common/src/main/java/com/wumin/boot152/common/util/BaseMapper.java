package com.wumin.boot152.common.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 生成实体对应mapper的基础接口
 * @param <T>
 */
public interface BaseMapper<T> extends Mapper<T>,MySqlMapper<T>{
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}

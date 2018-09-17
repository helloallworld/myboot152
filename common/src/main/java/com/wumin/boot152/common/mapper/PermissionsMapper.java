package com.wumin.boot152.common.mapper;

import com.wumin.boot152.common.entity.Permissions;
import com.wumin.boot152.common.util.BaseMapper;

import java.util.List;

public interface PermissionsMapper extends BaseMapper<Permissions> {
    List<Permissions> getPermissionsByUserId(Integer userId);
}
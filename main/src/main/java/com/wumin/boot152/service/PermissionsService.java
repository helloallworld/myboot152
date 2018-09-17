package com.wumin.boot152.service;

import com.wumin.boot152.common.entity.Permissions;

import java.util.List;

public interface PermissionsService {
    List<Permissions> getPermissionById(Integer id);
}

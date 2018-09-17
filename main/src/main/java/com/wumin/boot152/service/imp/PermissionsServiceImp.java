package com.wumin.boot152.service.imp;

import com.wumin.boot152.common.entity.Permissions;
import com.wumin.boot152.common.mapper.PermissionsMapper;
import com.wumin.boot152.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionsServiceImp implements PermissionsService {
    @Autowired
    private PermissionsMapper permissionsMapper;
    @Override
    public List<Permissions> getPermissionById(Integer id) {
        return permissionsMapper.getPermissionsByUserId(id);
    }
}

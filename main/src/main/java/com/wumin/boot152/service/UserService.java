package com.wumin.boot152.service;

import com.wumin.boot152.common.entity.User;

import java.util.List;

public interface UserService {
    List<User> getUserByUsername(String username);
}

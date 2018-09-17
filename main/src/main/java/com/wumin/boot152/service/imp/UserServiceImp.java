package com.wumin.boot152.service.imp;

import com.wumin.boot152.common.entity.User;
import com.wumin.boot152.common.mapper.UserMapper;
import com.wumin.boot152.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Resource
    private UserMapper userMapper;
    private static final Logger logger= LoggerFactory.getLogger(UserServiceImp.class);
    @Override
    public List<User> getUserByUsername(String username) {
        logger.info("正在查询");
        User user=new User();
        user.setUsername(username);
        List<User> users= userMapper.select(user);
        return users;
    }
}

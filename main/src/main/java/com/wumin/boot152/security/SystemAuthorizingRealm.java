package com.wumin.boot152.security;

import com.wumin.boot152.common.entity.Permissions;
import com.wumin.boot152.common.entity.User;
import com.wumin.boot152.common.util.PasswordUtils;
import com.wumin.boot152.security.exception.MoreUsersException;
import com.wumin.boot152.service.PermissionsService;
import com.wumin.boot152.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemAuthorizingRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;
    @Resource
    private PermissionsService permissionsService;
    /**
     *授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();
        List<Permissions> permissions = permissionsService.getPermissionById(user.getId());
        for (Permissions permission : permissions) {
            info.addStringPermission(permission.getCode());
        }
        return info;
    }

    /**
     *认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
        List<User> users=userService.getUserByUsername(token.getUsername());
        if(users==null){
            return null;
        }
        if (users.size() > 1) {
            throw new MoreUsersException("");
        }
        if (users.size() == 0) {
            return null;
        }
        User user = users.get(0);
        if (null != user) {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
            SimpleAuthenticationInfo info1=new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),ByteSource.Util.bytes(user.getSalt()),getName());
            return info;
        }
        return null;
    }

    /**
     * 设置加密方式，设置后subject中password会转成加密后的密码与token中的取对比
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-256");
        matcher.setHashIterations(1024);
        matcher.setStoredCredentialsHexEncoded(false);
        setCredentialsMatcher(matcher);
    }
}

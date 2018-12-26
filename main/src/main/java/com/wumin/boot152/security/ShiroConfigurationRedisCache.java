package com.wumin.boot152.security;

import com.wumin.boot152.service.PermissionsService;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 将cache从ehcache转到redis
 *
 *  依赖
             <!-- shiro+redis缓存插件 -->
             <dependency>
             <groupId>org.crazycake</groupId>
             <artifactId>shiro-redis</artifactId>
             <version>2.8.24</version>
             </dependency>

 */
//@Configuration
public class ShiroConfigurationRedisCache {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);
    /**
     * <!--shrio redis集成 -->
     *
     * @param redisConfig
     * @return
     */
    @Bean(name = "redisManager")
    public RedisManager redisManager(RedisProperties redisConfig) {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisConfig.getHost());
        redisManager.setPort(redisConfig.getPort());
        //redisManager.setExpire(3600);// 配置缓存过期时间
        redisManager.setTimeout(redisConfig.getTimeout());
        redisManager.setPassword(redisConfig.getPassword());
        redisManager.setDatabase(redisConfig.getDatabase());
        return redisManager;
    }

    /**
     * Session Manager
     * 默认Session为30分钟
     * 使用的是shiro-redis开源插件
     */
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager(RedisManager redisConfig) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisConfig);
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean(name = "redisSessionDAO")
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }


    @Bean(name = "securityManager")
    @Lazy
    public SecurityManager securityManagerMy(@Lazy SystemAuthorizingRealm systemAuthorizingRealm, CacheManager cacheManager, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(cacheManager);
        securityManager.setRealm(systemAuthorizingRealm);
        systemAuthorizingRealm.setCacheManager(cacheManager);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean,PermissionsService  permissionsServcie) {
        //拦截规则，
        //CaptchaFormAuthenticationFilter extends FormAuthenticationFilter（shiro认证）
        //MapLogoutFilter  extends org.apache.shiro.web.filter.authc.LogoutFilter(shiro Logout)
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc", new CaptchaFormAuthenticationFilter( permissionsServcie));
        filters.put("logout", new MapLogoutFilter());
        /////////////////////// 下面这些规则配置最好配置到配置文件中 ///////////////////////
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
        filterChainDefinitionMap.put("/myCode/**", "authc");// 这里为了测试，只限制/user，实际开发中请修改为具体拦截的请求规则
        // anon：它对应的过滤器里面是空的,什么都没做
        logger.info("##################从数据库读取权限规则，加载到shiroFilter中##################");
        filterChainDefinitionMap.put("/user/edit/**", "authc,perms[user:edit]");// 这里为了测试，固定写死的值，也可以从数据库或其他配置中读取
        //什么请求对应什么拦截规则
        filterChainDefinitionMap.put("/login", "authc");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**", "anon");//anon 可以理解为不拦截

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }
    /**
     * ShiroFilter
     *
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager,PermissionsService permissionsServcie) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的连接
        shiroFilterFactoryBean.setSuccessUrl("/user");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        //设置过滤链的私有方法
        loadShiroFilterChain(shiroFilterFactoryBean,permissionsServcie);
        return shiroFilterFactoryBean;
    }
}

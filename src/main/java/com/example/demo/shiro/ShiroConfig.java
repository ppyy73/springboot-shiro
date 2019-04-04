package com.example.demo.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pp shiro 配置类
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 添加内置过滤器
        /**
         * shiro 内置过滤器，可以实现权限相关的拦截器
         * 		 常用的过滤器:
         * 			anon: 无需认证（登陆）可以访问
         * 			authc： 必须认证才可以访问
         * 			user： 如果使用rememberMe的功能可以直接访问
         * 			perms： 该资源必须得到资源权限才可以访问
         * 			roles：该资源必须得到角色权限才可以访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();


        // 对‘/testThymeleaf’ 不拦截
        filterMap.put("/testThymeleaf", "anon");
        filterMap.put("/login", "anon");

//		授权过滤器
//		当授权拦截后，shiro会自动跳转到未授权界面
        filterMap.put("/update", "roles[user]");
        filterMap.put("/add", "roles[admin]");


        // 拦截所有
        filterMap.put("/*", "authc");

        // 修改调整登陆界面
        shiroFilterFactoryBean.setLoginUrl("/tologin");

//		设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager() {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联realm
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean(name = "userRealm")
    public UserRealm userRealm() {
        return new UserRealm();
    }
}

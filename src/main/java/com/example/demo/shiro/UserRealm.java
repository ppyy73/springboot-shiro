package com.example.demo.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;

public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	/**
	 * 执行授权逻辑
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		System.out.println("执行授权逻辑");
//		给资源进行授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		// 添加资源的授权字符串
		// info.addStringPermission("user:add");

		/**
		 * 到数据库查询当前登录用户的授权字符串 获取当前用户
		 */
		Subject subject = SecurityUtils.getSubject();

		/**
		 * user对象是从 执行认证逻辑中的 new SimpleAuthenticationInfo(user, user.getPassword(), "");
		 * 方法中获取的第一个参数
		 */
		User user = (User) subject.getPrincipal();

//		System.out.println(user.getName() + "  " + user.getRoles());

		User us = userService.getById(user.getId());
//		System.out.println(us.getName() + "  " + us.getRoles());
		String roles = us.getRoles();
		Set<String> set = new HashSet<>();
		set.add(roles);
		info.addRoles(set);

		return info;
	}

	/**
	 * 执行认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//		System.out.println("执行认证逻辑");
		/**
		 * 假设数据库用户名和密码
		 */
		// 编写shiro判断逻辑，判断用户名和密码
//		1.判断用户名
		UsernamePasswordToken tk = (UsernamePasswordToken) token;
		User user = userService.getUserByName(tk.getUsername());
		if (user == null) {
//			用户名不存在
//			shiro底层会抛出UnknownAccountException异常
			return null;
		}
//		判断密码
		return new SimpleAuthenticationInfo(user, user.getPassword(), "");
	}

}

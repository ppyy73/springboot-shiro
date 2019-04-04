package com.example.demo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		System.out.println("UserController.Helle()");
		return "ok";
	}

	@RequestMapping("/add")
	public String add() {
		return "/user/add";
	}

	@RequestMapping("/update")
	public String update() {
		return "/user/update";
	}

	@RequestMapping("/tologin")
	public String tologin() {
		return "/login";
	}
	
	@RequestMapping("/noAuth")
	public String noAuth() {
		return "/noAuth";
	}

	@RequestMapping("/login")
	public String login(String name, String password, Model model) {
		System.out.println("name= " + name);
		/**
		 * 使用shiro编写认证操作
		 */

//		1.获取Subject
		Subject subject = SecurityUtils.getSubject();

//		2.封装用户数据
		UsernamePasswordToken token = new UsernamePasswordToken(name, password);
//		System.out.println(token);
//		3.执行登陆方法
//		只要不出异常，表示登陆成功
		try {
			subject.login(token);
//			登陆成功跳转到test.html界面
			return "redirect:/testThymeleaf";

		} catch (UnknownAccountException e) {
//			登陆失败:用户名不存在
			// TODO: handle exception
			model.addAttribute("msg", "用户名不存在");
			return "login";
		} catch (IncorrectCredentialsException e) {
			// TODO: handle exception
//			登陆失败:密码错误
			model.addAttribute("msg", "密码错误");
			return "login";
		}

//		return "/login";
	}

	@RequestMapping("/testThymeleaf")
	public String testThymeleaf(Model model) {
		model.addAttribute("name", "hello testThymeleaf");
		return "test";
	}
}

package com.douzone.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(
			@RequestParam("name") String name, 
			@RequestParam("id") String id, 
			@RequestParam("password") String password,
			UserVo uservo
			) {
		uservo.setId(id);
		uservo.setName(name);
		uservo.setPassword(password);
		uservo.setBlogId(id);
		if(!(userService.join(uservo))) {
			return "main/index";
		}
		return "user/joinsuccess";
	}
}

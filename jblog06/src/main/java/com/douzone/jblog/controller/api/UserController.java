package com.douzone.jblog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;

@RestController("userControllerApi")
@RequestMapping("/user/api")
@ResponseBody
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/checkid")
	public JsonResult checkId(@RequestParam(value="id", required=true, defaultValue="") String id) {
		UserVo userVo = userService.getUser(id);
		System.out.println("=======================================" + id);
		Boolean data = userVo != null;
		
		// result.setResult("ok");
		// result.setData(data);
		
		return JsonResult.success(userVo != null);
	}
}

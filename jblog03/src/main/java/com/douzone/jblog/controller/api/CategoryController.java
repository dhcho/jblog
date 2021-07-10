package com.douzone.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.vo.CategoryVo;

@Controller("categoryControllerApi")
@RequestMapping("/{id:(?!assets).*}/admin/category/api")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@ResponseBody
	@RequestMapping("/list/{id}")
	public JsonResult list(@PathVariable("id") String id) {
		System.out.println("========================" + id);
		List<CategoryVo> list = categoryService.getList(id);

		return JsonResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public JsonResult add(@RequestBody CategoryVo vo) {
		// 등록 작업(CategoryService)
		categoryService.add(vo);
		return JsonResult.success(vo);
	}
	
	@ResponseBody
	@RequestMapping("/delete/{no}")
	public JsonResult delete(@PathVariable("no") int no, @RequestBody CategoryVo vo) {
		// 삭제 작업(CategoryService)
		int data = 0;
		
		Boolean chk = categoryService.delete(vo);
		
		if(!chk) {
			// 1. 삭제가 안된 경우
			data = -1;
		} else {
			// 2. 삭제가 성공한 경우
			data = no;
		}
		
		return JsonResult.success(data);
	}
}

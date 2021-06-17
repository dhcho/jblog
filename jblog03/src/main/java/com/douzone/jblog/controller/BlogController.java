package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.FileUploadService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping({"", "/{pathNo1}", "/{pathNo1}/{pathNo2}"})
	public String index(
			@PathVariable("id") String id,
			@PathVariable("pathNo1") Optional<Long> pathNo1,
			@PathVariable("pathNo2") Optional<Long> pathNo2) {
		Long categoryNo = 0L;
		Long postNo = 0L;
		
		if(pathNo2.isPresent()) {
			categoryNo = pathNo1.get();
			postNo = pathNo2.get();
		} else if(pathNo1.isPresent()) {
			categoryNo = pathNo1.get();
		}
		
		System.out.println("id:" + id);
		System.out.println("category:" + categoryNo);
		System.out.println("post:" + postNo);
		
		return "blog/main";
	}
	
	@RequestMapping(value="/admin/basic", method=RequestMethod.GET)
	public String adminBasic(@AuthUser UserVo authUser, Model model) {
		String id = authUser.getId();
		BlogVo vo = blogService.getBlog(id);
		model.addAttribute("vo", vo);
		return "blog/admin/basic";
	}
	
	@RequestMapping(value="/admin/basic", method=RequestMethod.POST)
	public String updateBasic(
			@AuthUser UserVo authUser,
			@RequestParam("title") String title,
			@RequestParam("logo-file") MultipartFile file,
			BlogVo vo) {
		String url = fileUploadService.restore(file);
		vo.setId(authUser.getId());
		vo.setTitle(title);
		vo.setLogo(url);
		
		blogService.updateBlog(vo);
		return "redirect:/blog/admin/basic";
	}
	
	@RequestMapping(value="/admin/category", method=RequestMethod.GET)
	public String adminCategory(@AuthUser UserVo uservo, Model model) {
		System.out.println(uservo.getId());
		List<CategoryVo> list = categoryService.getCategoryList(uservo.getId());
		model.addAttribute("list", list);
		return "blog/admin/category";
	}
	
	@RequestMapping(value="/admin/category/add", method=RequestMethod.POST)
	public String addCategory(
			@AuthUser UserVo authUser,
			@RequestParam("name") String name,
			@RequestParam("desc") String desc,
			CategoryVo vo) {
		vo.setBlogId(authUser.getId());
		vo.setName(name);
		vo.setDesc(desc);
		
		categoryService.add(vo);
		return "redirect:/blog/admin/category";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.GET)
	public String adminWrite(@AuthUser UserVo uservo) {
		System.out.println(uservo.getId());
		return "blog/admin/write";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String adminWrite(
			@AuthUser UserVo uservo,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			PostVo vo) {
		vo.setCategoryNo(2);
		vo.setTitle(title);
		vo.setContents(content);
		System.out.println("TEST-----------------------------------");
		
		postService.add(vo);
		return "redirect:/blog/admin/write";
	}
}

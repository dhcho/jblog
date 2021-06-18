package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

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
	ServletContext application;
	
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
			@PathVariable("pathNo1") Optional<Integer> pathNo1,
			@PathVariable("pathNo2") Optional<Integer> pathNo2,
			Model model) {
		int categoryNo = 0;
		int postNo = 0;
		
		List<PostVo> postList = postService.getList(id);
		List<CategoryVo> categoryList = categoryService.getList(id);
		PostVo postvo = postService.getLatestPost(id);
		String blogTitle = blogService.getBlog(id).getTitle();
		
		if(pathNo2.isPresent()) {
			categoryNo = pathNo1.get();
			postNo = pathNo2.get();
		} else if(pathNo1.isPresent()) {
			categoryNo = pathNo1.get();
		}
		
		model.addAttribute("postList", postList);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("post", postvo);
		
		application.setAttribute("blogTitle", blogTitle);
		
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
		List<CategoryVo> list = categoryService.getList(uservo.getId());
		model.addAttribute("list", list);
		return "blog/admin/category";
	}
	
	@RequestMapping(value="/admin/category", method=RequestMethod.POST)
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
	public String adminWrite(@AuthUser UserVo authUser, Model model) {
		List<CategoryVo> list = categoryService.getList(authUser.getId());
		model.addAttribute("list", list);
		return "blog/admin/write";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String adminWrite(
			@AuthUser UserVo authUser,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("category") int categoryNo,
			PostVo vo) {
		vo.setCategoryNo(categoryNo);
		vo.setTitle(title);
		vo.setContents(content);
		
		postService.add(vo);
		return "redirect:/" + authUser.getId();
	}
}

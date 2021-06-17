package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	public Boolean add(BlogVo vo) {
		return blogRepository.insert(vo);
	}
	
	public Boolean updateBlog(BlogVo vo) {
		return blogRepository.update(vo);
	}

	public BlogVo getBlog(String id) {
		return blogRepository.findBlog(id);
	}
}

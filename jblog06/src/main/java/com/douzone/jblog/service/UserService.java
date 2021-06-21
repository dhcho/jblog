package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional
	public Boolean join(UserVo uservo) {
		BlogVo blogvo = new BlogVo();
		CategoryVo categoryvo = new CategoryVo();
		blogvo.setId(uservo.getId());
		categoryvo.setBlogId(uservo.getId());
		Boolean chk;
		chk = userRepository.insert(uservo);
		chk = blogRepository.insert(blogvo);
		chk = categoryRepository.insert(categoryvo);
		
		return chk;
	}
	
	public UserVo login(UserVo uservo) {
		return userRepository.findUser(uservo);
	}
	
	public UserVo getUser(String id, String password) {
		return userRepository.findByIdAndPassword(id, password);
	}
	
	public UserVo getUser(String id) {
		return userRepository.findById(id);
	}
}

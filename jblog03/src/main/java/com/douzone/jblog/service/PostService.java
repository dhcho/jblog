package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.PostVo;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	
	public Boolean add(PostVo vo) {
		return postRepository.insert(vo);
	}

	public List<PostVo> getList(String id) {
		return postRepository.findList(id);
	}

	public PostVo getLatestPost(String id) {
		return postRepository.findLatestPost(id);
	}
}

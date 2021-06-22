package com.douzone.jblog.service;

import java.util.List;
import java.util.Map;

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

	public List<PostVo> getList(Map<String, Object> map) {
		return postRepository.findList(map);
	}

	public PostVo getPost(Map<String, Object> postMap) {
		return postRepository.findLatestPost(postMap);
	}

	public List<PostVo> getCondList(Map<String, Object> map) {
		return postRepository.findCondList(map);
	}
}

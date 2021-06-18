package com.douzone.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.PostVo;

@Repository
public class PostRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean insert(PostVo vo) {
		int count = sqlSession.insert("post.insert", vo);
		return count == 1;
	}

	public List<PostVo> findList(Map<String, Object> map) {
		return sqlSession.selectList("post.findList", map);
	}

	public PostVo findLatestPost(Map<String, Object> postMap) {
		return sqlSession.selectOne("post.findLatestPost", postMap);
	}

	public List<PostVo> findCondList(Map<String, Object> map) {
		return sqlSession.selectList("post.findCondList", map);
	}
}

package com.douzone.jblog.repository;

import java.util.List;

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

	public List<PostVo> findList(String id) {
		return sqlSession.selectList("post.findList", id);
	}

	public PostVo findLatestPost(String id) {
		return sqlSession.selectOne("post.findLatestPost", id);
	}
}

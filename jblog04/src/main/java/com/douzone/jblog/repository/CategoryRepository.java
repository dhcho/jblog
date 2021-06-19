package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean insert(CategoryVo vo) {
		int count = sqlSession.insert("category.insert", vo);
		return count == 1;
	}

	public List<CategoryVo> findList(String id) {
		return sqlSession.selectList("category.findList", id);
	}

	public int delete(CategoryVo vo) {
		return sqlSession.delete("category.delete", vo);
	}
}

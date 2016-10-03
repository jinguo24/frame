package com.simple.common.mybatis.dao;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.spring.SpringUtil;

public class BaseIbatisDao extends SqlSessionDaoSupport{
	protected final Log log = LogFactory.getLog(getClass());

	protected String clientId = this.getClass().getAnnotation(DatabaseTemplate.class).value();
	private SqlSessionTemplate sqlSession1 = (SqlSessionTemplate) SpringUtil.getBean(clientId);
	protected SqlSession sqlSession = null;
	
	public BaseIbatisDao() {
		setSqlSessionTemplate();
	}
	
	public void setSqlSessionTemplate() {
		super.setSqlSessionTemplate(sqlSession1);
		sqlSession = this.getSqlSession();
	}

	public Object getById(final String methodId,final Object primaryKey)
	{
		return sqlSession.selectOne(methodId, primaryKey);
	}

	public int createObject(final String methodId,final Object o) {
		return sqlSession.insert(methodId, o);
	}
	
	public int deleteDBData(final String methodId,final Object primaryKey) {
		return sqlSession.delete(methodId, primaryKey);
	}
	
	public int updateObject(final String methodId,final Object o) {
		return sqlSession.update(methodId, o);
	}
	
	public Object selectOne(String statement, Map<String, Object> params){
		return sqlSession.selectOne(statement, params);
	}
}

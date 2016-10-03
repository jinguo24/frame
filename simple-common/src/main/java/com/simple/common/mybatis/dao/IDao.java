package com.simple.common.mybatis.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c)  2015-3-12</p>
 * <p>Company: lenovo</p>
 * @author <a href="mailto:soldiers888@gmail.com">panbing.wu</a>
 * @version 1.0  2015-3-12 下午3:16:57
 */
public interface IDao{

	//Page findByPageRequest(final PageRequest pr);
	
	Object getById(Object id);
	
//
//	void deleteById(PK id);
//
//	void deleteBy(Object... params);
//	
//	void delete(Collection<PK> ids);
//
//	Object save(E entity);
//	
//	Object saveWithLog(Object logInfo,E entity);
//
//	int update(E entity);
//	
//	int updateWithLog(Object logInfo,E entity);
//
//	void saveOrUpdate(E entity);
//	
//	void saveOrUpdateWithLog(Object logInfo,E entity);
//
//	boolean isUnique(E entity, String uniquePropertyNames);
//
//	boolean isPropertyUnique(String property, String orgValue, String newValue);
//	
//	List<E> findAll();
//
//	List<E> findBy(final Object... params);
//
//	List<E> findByMap(Map map);
//
//	E findUniqueBy(final Object... params);
//
//	void flush();
//
//	void deleteByWithLog(Object logInfo, final Object... params);

}

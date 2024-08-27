package com.coursecraft.daos;

import java.util.List;
import java.util.UUID;

import com.coursecraft.entities.User;

public interface AppDao {

	public <T> T save(T entity);

	public <T> T findById(Class<T> entityClass, int id);

	public User findBySessionId(UUID sessionId);

	public <T> List<T> queryWith(Class<T> entityClass, String key, String value, int limit);

	public void delete(Object entity);

	public void delete(Class<?> entityClass, int id);

}

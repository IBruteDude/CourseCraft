package com.coursecraft.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.coursecraft.entity.User;

import jakarta.persistence.EntityManager;

public interface AppDao {

	public EntityManager getEntityManager();

	public <T> T save(T entity);

	public <T> T findById(Class<T> entityClass, int id);

	public Optional<User> findBySessionId(String sessionId);

	public <T> List<T> search(Class<T> entityClass, String key, String value, int limit);

	public <T> List<T> queryWith(Class<T> entityClass, String key, Object value, int limit);

	public void delete(Object entity);

	public void delete(Class<?> entityClass, int id);

    public void truncateTable(String tableName);

}

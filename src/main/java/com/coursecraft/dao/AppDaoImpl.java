package com.coursecraft.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.coursecraft.entity.User;
import com.coursecraft.entity.UserSession;

@Repository
public class AppDaoImpl implements AppDao {

	private EntityManager entityManager;

	public AppDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	@Transactional
	public <T> T save(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public <T> List<T> search(Class<T> entityClass, String key, String value, int limit) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityClass);

		Root<T> root = cq.from(entityClass);

		TypedQuery<T> query = entityManager
				.createQuery(cq.select(root).where(cb.like(root.get(key), '%' + value + '%')));

		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public <T> List<T> queryWith(Class<T> entityClass, String key, Object value, int limit) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityClass);
		Root<T> root = cq.from(entityClass);
		TypedQuery<T> query = entityManager.createQuery(cq.select(root).where(cb.equal(root.get(key), value)));

		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public <T> T findById(Class<T> entityClass, int id) {
		return entityManager.find(entityClass, id);
	}

	@Override
	public Optional<User> findBySessionId(String sessionId) {
		TypedQuery<UserSession> query = entityManager.createQuery("FROM UserSession WHERE sessionUuid=:sessionId",
				UserSession.class);

		query.setParameter("sessionId", UUID.fromString(sessionId));

		try {
			UserSession userSession = query.getSingleResult();
			return Optional.ofNullable(userSession.getUser());
		} catch (Exception e) {
			return Optional.ofNullable(null);
		}

	}

	public void delete(Object entity) {
		entityManager.remove(entity);
	}

	public void delete(Class<?> entityClass, int id) {
		entityManager.remove(findById(entityClass, id));
	}

	@Transactional
	public void truncateTable(String tableName) {
		entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
	}

}

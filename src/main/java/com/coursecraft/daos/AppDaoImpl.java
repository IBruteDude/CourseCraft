package com.coursecraft.daos;

import com.coursecraft.entities.User;
import com.coursecraft.entities.UserSession;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public class AppDaoImpl implements AppDao {

	private EntityManager entityManager;

	public AppDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public <T> T save(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public <T> List<T> queryWith(Class<T> entityClass, String key, String value, int limit) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityClass);

		// TypedQuery<User> query = entityManager.createQuery("FROM User WHERE
		// "+key+"=:value", User.class);
		// query.setParameter("value", value);
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
	public User findBySessionId(UUID sessionId) {
		TypedQuery<UserSession> query = entityManager.createQuery("FROM UserSession WHERE sessionUuid=:sessionId",
				UserSession.class);

		query.setParameter("sessionId", sessionId);

		UserSession userSession = query.getSingleResult();
		return userSession.getUser();
	}

	public void delete(Object entity) {
		entityManager.remove(entity);
	}

	public void delete(Class<?> entityClass, int id) {
		entityManager.remove(findById(entityClass, id));
	}

}

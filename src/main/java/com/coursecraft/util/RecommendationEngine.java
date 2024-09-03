package com.coursecraft.util;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursecraft.dao.AppDao;
import com.coursecraft.dto.RecommendationsDto;
import com.coursecraft.entity.Course;
import com.coursecraft.entity.User;

import jakarta.persistence.TypedQuery;

@Service
public class RecommendationEngine {
	@Autowired
	private AppDao appDao;

	public List<Course> getRecommendations(Optional<User> student, RecommendationsDto recommendationsParams,
			int limit) {
		TypedQuery<Course> query = appDao.getEntityManager()
				.createQuery("SELECT e FROM Course e ORDER BY FUNCTION('RAND')", Course.class);

		query.setMaxResults(limit);
		return query.getResultList();
	}
}

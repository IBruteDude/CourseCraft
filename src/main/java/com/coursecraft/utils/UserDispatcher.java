package com.coursecraft.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

import com.coursecraft.daos.AppDao;
import com.coursecraft.entities.User;

@Component
public class UserDispatcher<T> {

	private AppDao appDao;
	private User.Role userType;

	public UserDispatcher(AppDao appDao) {
		this.appDao = appDao;
	}

	private T adminChoice;
	private T instructorChoice;
	private T studentChoice;
	private T notFoundChoice;

	public UserDispatcher<T> sessionId(String sessionId) {
		try {
			if (sessionId != null) {
				User user = appDao.findBySessionId(UUID.fromString(sessionId));
				this.userType = user.getRole();
			} else {
				this.userType = User.Role.NOT_REGISTERED;
			}
		} catch (jakarta.persistence.NoResultException e) {
			this.userType = User.Role.NOT_REGISTERED;
		}

		return this;
	}

	public UserDispatcher<T> admin(T adminChoice) {
		this.adminChoice = adminChoice;
		return this;
	}

	public UserDispatcher<T> instructor(T instructorChoice) {
		this.instructorChoice = instructorChoice;
		return this;
	}

	public UserDispatcher<T> student(T studentChoice) {
		this.studentChoice = studentChoice;
		return this;
	}

	public UserDispatcher<T> notFound(T notFoundChoice) {
		this.notFoundChoice = notFoundChoice;
		return this;
	}

	public T select() throws MissingHandlerException {
		switch (this.userType) {
			case User.Role.ADMIN -> {
				if (this.adminChoice == null)
					throw new MissingHandlerException("Admin");
				return this.adminChoice;
			}
			case User.Role.INSTRUCTOR -> {
				if (this.instructorChoice == null)
					throw new MissingHandlerException("Instructor");
				return this.instructorChoice;
			}
			case User.Role.STUDENT -> {
				if (this.studentChoice == null)
					throw new MissingHandlerException("Student");
				return this.studentChoice;
			}
			case User.Role.NOT_REGISTERED -> {
				if (this.notFoundChoice == null)
					throw new MissingHandlerException("NotFound");
				return this.notFoundChoice;
			}
			default -> {
				throw new MissingHandlerException("Undefined");
			}
		}
	}

	public static class MissingHandlerException extends RuntimeException {
		public MissingHandlerException(String role) {
			super("No handler for " + role + " case was found!");
		}
	}
}

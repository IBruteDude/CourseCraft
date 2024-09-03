package com.coursecraft.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.coursecraft.dao.AppDao;
import com.coursecraft.entity.User;
import com.coursecraft.entity.UserSession;

@Component
public class UserDispatcher<T> {

	private AppDao appDao;
	private String adminSessionId;
	private String studentSessionId;
	private String instructorSessionId;

	public UserDispatcher(AppDao appDao) {
		this.appDao = appDao;
	}

	private T adminChoice;
	private T instructorChoice;
	private T studentChoice;
	private T notFoundChoice;

	public UserDispatcher<T> adminSessionId(String sessionId) {
		this.adminSessionId = sessionId;
		return this;
	}

	public UserDispatcher<T> instructorSessionId(String sessionId) {
		this.instructorSessionId = sessionId;
		return this;
	}

	public UserDispatcher<T> studentSessionId(String sessionId) {
		this.studentSessionId = sessionId;
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

	private Optional<UserSession> checkSessionIdExists(String sessionId) {
		if (sessionId == null)
			return Optional.ofNullable(null);

		List<UserSession> sessions = appDao.queryWith(UserSession.class, "sessionUuid", UUID.fromString(sessionId), 1);

		if (sessions.isEmpty())
			return Optional.ofNullable(null);

		UserSession session = sessions.getFirst();

		if (session.isExpired())
			return Optional.ofNullable(null);

		if (session.getExpiryDate().isAfter(LocalDateTime.now())) {
			session.setExpired(true);
			appDao.update(session);
			return Optional.ofNullable(null);
		}
		return Optional.of(session);
	}

	public T select() throws MissingHandlerException {
		Optional<UserSession> adminSession = checkSessionIdExists(adminSessionId);
		if (adminSession.isPresent())
			if (adminSession.get().getUser().getRole() == User.Role.ADMIN) {
				if (this.adminChoice == null)
					throw new MissingHandlerException("Admin");
				return this.adminChoice;
			}

		Optional<UserSession> instructorSession = checkSessionIdExists(instructorSessionId);
		if (instructorSession.isPresent())
			if (instructorSession.get().getUser().getRole() == User.Role.INSTRUCTOR) {
				if (this.instructorChoice == null)
					throw new MissingHandlerException("Instructor");
				return this.instructorChoice;
			}

		Optional<UserSession> studentSession = checkSessionIdExists(studentSessionId);
		if (studentSession.isPresent())
			if (studentSession.get().getUser().getRole() == User.Role.STUDENT) {
				if (this.studentChoice == null)
					throw new MissingHandlerException("Student");
				return this.studentChoice;
			}
		if (this.notFoundChoice == null)
			throw new MissingHandlerException("NotFound");
		return this.notFoundChoice;
	}

	public static class MissingHandlerException extends RuntimeException {
		public MissingHandlerException(String role) {
			super("No handler for " + role + " case was found!");
		}
	}

}

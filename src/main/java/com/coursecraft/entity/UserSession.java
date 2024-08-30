package com.coursecraft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

import jakarta.persistence.Column;

@Entity
@Table(name = "user_session")
@Data
@NoArgsConstructor
public class UserSession {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Column(name = "session_uuid", nullable = false)
	protected UUID sessionUuid;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	protected User user;

	public UserSession(User user) {
		this.user = user;
		this.sessionUuid = UUID.randomUUID();
	}

	public UserSession(User user, String sessionUuid) {
		this.user = user;
		this.sessionUuid = UUID.fromString(sessionUuid);
	}

	public String toString() {
		return "UserSession(id=" + id + ", sessionUuid=" + sessionUuid + ", user_id=" + user.getId() + ")";
	}

}

package com.coursecraft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

	@Column(name = "expiry_date", nullable = false)
	protected LocalDateTime expiryDate;

	@Column(name = "expired", nullable = false)
	protected boolean expired;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	protected User user;

	public UserSession(User user, int expiry) {
		this(user, expiry, UUID.randomUUID().toString());
	}

	public UserSession(User user, int expiry, String sessionUuid) {
		this.user = user;
		this.expired = false;
		this.expiryDate = LocalDateTime.ofEpochSecond(Instant.now().plusSeconds(expiry).getEpochSecond(), 0,
				ZoneOffset.UTC);
		this.sessionUuid = UUID.fromString(sessionUuid);
	}

	public String toString() {
		return "UserSession(id=" + id + ", sessionUuid=" + sessionUuid + ", user_id=" + user.getId() + ")";
	}

}

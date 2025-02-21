package com.coursecraft.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Base implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	protected UUID id;

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	protected Instant createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	protected Instant updatedAt;

}

package com.coursecraft.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

import com.coursecraft.constant.Authority;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class User extends Base {

    @Column(name = "username", length = 50, nullable = false, unique = true)
    protected String username;

    @Column(name = "username", insertable = false, updatable = false)
    protected String email;

	@Column(name = "password", length = 500, nullable = false)
	protected String password;

    @Column(name = "enabled", nullable = false)
    protected boolean enabled;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    protected Authority authority = Authority.GUEST;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected Profile profile;

	// - Contact Information
	// - associated Cart

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	protected Set<UserSession> userSessions;
}

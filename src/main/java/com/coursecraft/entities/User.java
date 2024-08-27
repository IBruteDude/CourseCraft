package com.coursecraft.entities;

import java.util.Set;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor
public class User {
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static enum Role {
        ADMIN,
        INSTRUCTOR,
        STUDENT,
        NOT_REGISTERED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "email", nullable = false, unique = true)
    protected String email;

    @Column(name = "password", nullable = false)
    protected String password;

    @Enumerated
    protected Role role;

    @Column(name = "first_name")
    protected String firstName;

    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "profile_picture_uri")
    protected String profilePictureUri;

    // - Contact Information
    // - associated Cart
    // - Residence Country



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    protected Set<UserSession> userSessions;

    public String toString() {
        return (new com.coursecraft.utils.ToStringGenerator(this.getClass())).fieldListString();
    }

}

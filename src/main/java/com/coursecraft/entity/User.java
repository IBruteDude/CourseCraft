package com.coursecraft.entity;

import java.util.Set;

import com.coursecraft.dto.SignupDto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@ToString
@NoArgsConstructor
public class User {

    public User(String email, String password, String firstName, String lastName, String country) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.residenceCountry = Country.valueOf(country);
    }

    public User(SignupDto signupDto) {
        this(signupDto.getEmail(), signupDto.getPassword(), signupDto.getFirstName(), signupDto.getLastName(),
                signupDto.getCountry());
    }

    public static enum Role {
        ADMIN,
        INSTRUCTOR,
        STUDENT,
        NOT_REGISTERED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

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

    @Enumerated
    protected Country residenceCountry;

    @Column(name = "profile_picture_uri")
    protected String profilePictureUri;

    // - Contact Information
    // - associated Cart

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    protected Set<UserSession> userSessions;

    // public String toString() {
    // return (new
    // com.coursecraft.util.ToStringGenerator(this.getClass())).fieldListString();
    // }

}

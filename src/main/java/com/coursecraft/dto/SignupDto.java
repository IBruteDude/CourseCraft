package com.coursecraft.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class SignupDto {

    @NotNull
    @NotBlank
    @Email
    public String email;

    @NotNull
    @NotBlank
    public String password;

    @NotNull
    public String role;

    @NotNull
    @NotBlank
    public String firstName;

    @NotNull
    @NotBlank
    public String lastName;

    @NotNull
    @NotBlank
    public String country;

}

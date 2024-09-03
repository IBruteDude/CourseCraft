package com.coursecraft.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
public class LoginDto {

    @NotNull
    @NotEmpty
    @Email
    public String email;

    @NotNull
    @NotEmpty
    public String password;

}

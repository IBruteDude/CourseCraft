package com.coursecraft.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
public class LoginDto {

    @NotNull
    @NotEmpty
    @Email
    private String email;

    private String password;

}

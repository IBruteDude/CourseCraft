package com.coursecraft.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
public class LoginDto {
    public static class LoginDtoJsonView {
    }

    @JsonView(LoginDtoJsonView.class)
    @NotNull
    @NotEmpty
    @Email
    private String email;

    @JsonView(LoginDtoJsonView.class)
    private String password;

}

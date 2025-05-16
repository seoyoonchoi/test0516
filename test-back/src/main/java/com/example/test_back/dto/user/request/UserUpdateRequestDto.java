package com.example.test_back.dto.user.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    @NotNull(message = "password is mandatory")
    private String password;
    @NotNull(message = "confirmPassword is mandatory")
    private String confirmPassword;
}
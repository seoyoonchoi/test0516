package com.example.test_back.dto.user.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInRequestDto {
    @NotNull(message = "username is mandatory")
    private String username;

    @NotNull(message = "password is mandatory")
    private String password;
}
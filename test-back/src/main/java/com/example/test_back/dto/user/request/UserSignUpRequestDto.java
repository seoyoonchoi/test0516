package com.example.test_back.dto.user.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDto {
    @NotNull
    private String username;
    @NotNull
    private String role;
    @NotNull
    private String password; // 비밀번호
    @NotNull
    private String confirmPassword; // 비밀번호 확인
}

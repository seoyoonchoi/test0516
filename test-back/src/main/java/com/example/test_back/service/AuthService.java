package com.example.test_back.service;

import com.example.test_back.dto.response.ResponseDto;
import com.example.test_back.dto.user.request.UserSignInRequestDto;
import com.example.test_back.dto.user.request.UserSignUpRequestDto;
import com.example.test_back.dto.user.response.UserSignInResponseDto;
import com.example.test_back.dto.user.response.UserSignUpResponseDto;
import jakarta.validation.Valid;

public interface AuthService {
    ResponseDto<UserSignUpResponseDto> signup(@Valid UserSignUpRequestDto dto);
    ResponseDto<UserSignInResponseDto> login(@Valid UserSignInRequestDto dto);
}

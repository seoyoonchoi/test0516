package com.example.test_back.service;

import com.example.test_back.dto.response.ResponseDto;
import com.example.test_back.dto.user.request.UserUpdateRequestDto;
import com.example.test_back.dto.user.response.GetUserResponseDto;
import jakarta.validation.Valid;

public interface UserService {
    ResponseDto<GetUserResponseDto> getUserInfo(String userEmail);
    ResponseDto<GetUserResponseDto> updateUserInfo(String userEmail, @Valid UserUpdateRequestDto dto);
    ResponseDto<Void> deleteUser(String userEmail);
}
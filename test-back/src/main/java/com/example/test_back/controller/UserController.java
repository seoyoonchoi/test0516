package com.example.test_back.controller;

import com.example.test_back.common.ApiMappingPattern;
import com.example.test_back.dto.response.ResponseDto;
import com.example.test_back.dto.user.request.UserUpdateRequestDto;
import com.example.test_back.dto.user.response.GetUserResponseDto;
import com.example.test_back.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.USER_API)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // === UserController mapping pattern === //
    private static final String GET_USER_INFO = "/me";
    private static final String PUT_USER_INFO = "/me";
    private static final String DELETE_USER = "/me";

    // 1) 회원 정보 조회
    @GetMapping(GET_USER_INFO)
    public ResponseEntity<ResponseDto<GetUserResponseDto>> getUserInfo(
            // SecurityContextHolder에 저장된 인증 객체의 principal을 가져와서 사용
            // : 현재 인증된(로그인된) 사용자의 정보를 가져오는 애너테이션
            @AuthenticationPrincipal String userEmail
    ) {
        ResponseDto<GetUserResponseDto> response = userService.getUserInfo(userEmail);
        return ResponseEntity.ok(response);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 2) 회원 정보 수정
    @PutMapping(PUT_USER_INFO)
    public ResponseEntity<ResponseDto<GetUserResponseDto>> updateUserInfo(
            @AuthenticationPrincipal String userEmail,
            @Valid @RequestBody UserUpdateRequestDto dto
    ) {
        ResponseDto<GetUserResponseDto> response = userService.updateUserInfo(userEmail, dto);
        return ResponseEntity.ok(response);
    }

    // 3) 회원 탈퇴
    @DeleteMapping(DELETE_USER)
    public ResponseEntity<ResponseDto<Void>> deleteUser(
            @AuthenticationPrincipal String userEmail
    ){
        ResponseDto<Void> response = userService.deleteUser(userEmail);
        return ResponseEntity.noContent().build();
    }
}

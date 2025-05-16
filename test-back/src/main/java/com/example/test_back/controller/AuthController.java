package com.example.test_back.controller;

import com.example.test_back.common.ApiMappingPattern;
import com.example.test_back.dto.response.ResponseDto;
import com.example.test_back.dto.user.request.UserSignInRequestDto;
import com.example.test_back.dto.user.request.UserSignUpRequestDto;
import com.example.test_back.dto.user.response.UserSignInResponseDto;
import com.example.test_back.dto.user.response.UserSignUpResponseDto;
import com.example.test_back.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiMappingPattern.AUTH_API)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // === AuthController mapping pattern === //
    private static final String POST_SIGN_UP = "/signup";
    private static final String POST_SIGN_IN = "/login";

    // 1) 회원가입
    // - HTTP 메서드: POST
    // - URI 경로: /signup
    // @Params: UserSignUpRequestDto
    // @Return: UserSignUpResponseDto
    @PostMapping(POST_SIGN_UP)
    public ResponseEntity<ResponseDto<UserSignUpResponseDto>> signup(@Valid @RequestBody UserSignUpRequestDto dto) {
        System.out.println("=== 회원가입 요청 도착 ===");
        ResponseDto<UserSignUpResponseDto> response = authService.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2) 로그인
    // - HTTP 메서드: POST
    // - URI 경로: /login
    // @Params: UserSignInRequestDto
    // @Return: UserSignInResponseDto

    // GET VS "POST"
    // : POST 사용을 권장
    // - 로그인 과정에서 사용자 이름과 비밀번호와 같은 민감한 데이터를 서버로 전송하기 때문
    // cf) GET 요청은 URL에 데이터가 노출(데이터 조회 시 데이터 구분값(PK) 요청 시 사용)
    @PostMapping(POST_SIGN_IN)
    public ResponseEntity<ResponseDto<UserSignInResponseDto>> login(@Valid @RequestBody UserSignInRequestDto dto) {
        ResponseDto<UserSignInResponseDto> response = authService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
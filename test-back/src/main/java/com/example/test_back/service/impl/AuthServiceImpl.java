package com.example.test_back.service.impl;

import com.example.test_back.common.ResponseMessage;
import com.example.test_back.dto.response.ResponseDto;
import com.example.test_back.dto.user.request.UserSignInRequestDto;
import com.example.test_back.dto.user.request.UserSignUpRequestDto;
import com.example.test_back.dto.user.response.UserSignInResponseDto;
import com.example.test_back.dto.user.response.UserSignUpResponseDto;
import com.example.test_back.entity.User;
import com.example.test_back.provider.JwtProvider;
import com.example.test_back.repository.UserRepository;
import com.example.test_back.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;
    // 1) 회원 가입
    @Override
    public ResponseDto<UserSignUpResponseDto> signup(UserSignUpRequestDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();
        String role = dto.getRole();
        System.out.println(password);

        UserSignUpResponseDto data = null;
        User user = null;

        // 패스워드 일치 여부 확인
        if (!password.equals(confirmPassword)) {
            // 일치하지 않은 경우
            return ResponseDto.setFailed(ResponseMessage.NOT_MATCH_PASSWORD);
        }

        // 중복되는 사용자 검증
        if (userRepository.existsByUsername(username)) {
            // 중복 되는 경우 (사용할 수 X)
            return ResponseDto.setFailed(ResponseMessage.EXIST_DATA);
        }

        // 패스워드 암호화
        String encodePassword = bCryptPasswordEncoder.encode(password);

        // 권한 정보 확인
        User userRole = (User) userRepository.findByRole("USER")
                .orElseGet(() -> userRepository.save(User.builder().role("USER").build()));



        user = User.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
        System.out.println(user.getPassword());

        userRepository.save(user);

        data = new UserSignUpResponseDto(user);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    // 2) 로그인
    @Override
    public ResponseDto<UserSignInResponseDto> login(UserSignInRequestDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        UserSignInResponseDto data = null;
        User user = null;

        int exprTime = jwtProvider.getExpiration();

        user = (User) userRepository.findByUsername(username)
                .orElse(null);

        if (user == null) {
            return ResponseDto.setFailed(ResponseMessage.NOT_EXISTS_USER);
        }

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            // .matches(평문 비밀번호, 암호화된 비밀번호)
            // : 평문 비밀번호(실제 비밀번호)와 암호화된 비밀번호를 비교하여 일치 여부 반환(boolean)
            return ResponseDto.setFailed(ResponseMessage.NOT_MATCH_PASSWORD);
        }

        // 사용자 정보의 권한 정보를 호출
        String roles = user.getRole();

        String token = jwtProvider.generateJwtToken(username, Collections.singleton(roles)); // username에 email 저장

        data = new UserSignInResponseDto(token, user, exprTime);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }
}
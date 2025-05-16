package com.example.test_back.service.impl;

import com.example.test_back.common.ResponseMessage;
import com.example.test_back.dto.response.ResponseDto;
import com.example.test_back.dto.user.request.UserUpdateRequestDto;
import com.example.test_back.dto.user.response.GetUserResponseDto;
import com.example.test_back.entity.User;
import com.example.test_back.repository.UserRepository;
import com.example.test_back.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 1) 회원 정보 조회
    @Override
    public ResponseDto<GetUserResponseDto> getUserInfo(String username) {

        User user = (User) userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return ResponseDto.setFailed(ResponseMessage.NOT_EXISTS_USER);
        }

        GetUserResponseDto data = new GetUserResponseDto(user);

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<GetUserResponseDto> updateUserInfo(String username, UserUpdateRequestDto dto) {

//        User user = userRepository.findByEmail(userEmail).orElse(null);
//
//        if (user == null) {
//            return ResponseDto.setFailed(ResponseMessage.NOT_EXISTS_USER);
//        }

        User user = (User) userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_USER));


        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            // 비밀번호 정상
            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                // 비밀번호 확인과 일치하지 않음
//                return ResponseDto.setFailed(ResponseMessage.NOT_MATCH_PASSWORD);
                throw new IllegalArgumentException(ResponseMessage.NOT_MATCH_PASSWORD);
            }

            // 비밀번호 정상 + 일치
            String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());
            user.setPassword(encodedPassword);
        }


        GetUserResponseDto data = new GetUserResponseDto(user);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<Void> deleteUser(String username) {

        User user = (User) userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_USER));

        userRepository.delete(user);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
    }
}
package com.example.test_back.service.impl;

import com.example.test_back.common.ResponseMessage;
import com.example.test_back.dto.admin.request.PutAuthorityRequestDto;
import com.example.test_back.dto.admin.response.DemoteFromAdminResponseDto;
import com.example.test_back.dto.admin.response.PromoteToAdminResponseDto;
import com.example.test_back.dto.response.ResponseDto;
import com.example.test_back.entity.User;
import com.example.test_back.repository.UserRepository;
import com.example.test_back.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ResponseDto<PromoteToAdminResponseDto> promoteUserToAdmin(PutAuthorityRequestDto dto) {
        User user = (User) userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXISTS_USER));

        if ("ADMIN".equals(user.getRole())) {
            throw new IllegalStateException("이미 ADMIN 권한을 가지고 있습니다.");
        }

        user.setRole("ADMIN");
        User savedUser = userRepository.save(user);

        PromoteToAdminResponseDto data = PromoteToAdminResponseDto.builder()
                .username(savedUser.getUsername())
                .role(savedUser.getRole())
                .message("권한이 정상적으로 ADMIN으로 승격되었습니다.")
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional
    public ResponseDto<DemoteFromAdminResponseDto> demoteUserFromAdmin(PutAuthorityRequestDto dto) {
        User user = (User) userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("해당 이름의 사용자가 존재하지 않습니다."));

        if ("USER".equals(user.getRole())) {
            throw new IllegalStateException("이미 USER 권한입니다.");
        }

        user.setRole("USER");
        User savedUser = userRepository.save(user);

        DemoteFromAdminResponseDto data = DemoteFromAdminResponseDto.builder()
                .username(savedUser.getUsername())
                .role(savedUser.getRole())
                .message("ADMIN 권한이 성공적으로 해제되었습니다.")
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    private String getCurrentAdminUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "unknown";
    }
}

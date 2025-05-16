package com.example.test_back.dto.admin.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PromoteToAdminResponseDto {
    private String username; 
    private String role; // 가지고 있는 권한
    private String message;
}

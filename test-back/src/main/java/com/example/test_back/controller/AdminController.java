package com.example.test_back.controller;

import com.example.test_back.common.ApiMappingPattern;
import com.example.test_back.dto.admin.request.PutAuthorityRequestDto;
import com.example.test_back.dto.admin.response.DemoteFromAdminResponseDto;
import com.example.test_back.dto.admin.response.PromoteToAdminResponseDto;
import com.example.test_back.dto.response.ResponseDto;
import com.example.test_back.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.ADMIN_API)
public class AdminController {
    private final AdminService adminService;

    // == AdminController mapping pattern == //
    private static final String PUT_AUTHORITY_TO_ADMIN = "/promote";
    private static final String PUT_AUTHORITY_DEMOTE = "/demote";

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(PUT_AUTHORITY_TO_ADMIN)
    public ResponseEntity<ResponseDto<PromoteToAdminResponseDto>> promoteUserToAdmin(
            @RequestBody PutAuthorityRequestDto dto
    ) {
        ResponseDto<PromoteToAdminResponseDto> response = adminService.promoteUserToAdmin(dto);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(PUT_AUTHORITY_DEMOTE)
    public ResponseEntity<ResponseDto<DemoteFromAdminResponseDto>> demoteUserFromAdmin(
            @RequestBody PutAuthorityRequestDto dto
    ) {
        ResponseDto<DemoteFromAdminResponseDto> response = adminService.demoteUserFromAdmin(dto);
        return ResponseEntity.ok(response);
    }
}
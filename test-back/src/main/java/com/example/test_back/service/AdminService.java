package com.example.test_back.service;

import com.example.test_back.dto.admin.request.PutAuthorityRequestDto;
import com.example.test_back.dto.admin.response.DemoteFromAdminResponseDto;
import com.example.test_back.dto.admin.response.PromoteToAdminResponseDto;
import com.example.test_back.dto.response.ResponseDto;

public interface AdminService {
    ResponseDto<PromoteToAdminResponseDto> promoteUserToAdmin(PutAuthorityRequestDto dto);

    ResponseDto<DemoteFromAdminResponseDto> demoteUserFromAdmin(PutAuthorityRequestDto dto);
}

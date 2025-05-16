package com.example.test_back.service;


import com.example.test_back.dto.admin.request.NoticeRequestDto;
import com.example.test_back.dto.admin.response.NoticeResponseDto;
import com.example.test_back.dto.response.ResponseDto;
import jakarta.validation.Valid;

public interface NoticeService {
    ResponseDto<NoticeResponseDto> createNotice(@Valid NoticeRequestDto dto);
}

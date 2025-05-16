package com.example.test_back.controller;

import com.example.test_back.common.ApiMappingPattern;
import com.example.test_back.dto.admin.request.NoticeRequestDto;
import com.example.test_back.dto.admin.response.NoticeResponseDto;
import com.example.test_back.dto.response.ResponseDto;
import com.example.test_back.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiMappingPattern.NOTICE_API)
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<ResponseDto<NoticeResponseDto>> createNotice(@Valid @RequestBody NoticeRequestDto dto){
        ResponseDto<NoticeResponseDto> notice = noticeService.createNotice(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(notice);
    }

}

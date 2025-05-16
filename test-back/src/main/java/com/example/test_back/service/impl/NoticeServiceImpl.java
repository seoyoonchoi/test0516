package com.example.test_back.service.impl;

import com.example.test_back.dto.admin.request.NoticeRequestDto;
import com.example.test_back.dto.admin.response.NoticeResponseDto;
import com.example.test_back.dto.response.ResponseDto;
import com.example.test_back.entity.Notice;
import com.example.test_back.repository.NoticeRepository;
import com.example.test_back.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    @Transactional
    public ResponseDto<NoticeResponseDto> createNotice(NoticeRequestDto dto) {
        NoticeResponseDto responseDto = null;
        Notice newNotice = Notice.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        Notice saved = noticeRepository.save(newNotice);

        responseDto = NoticeResponseDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .adminUsername(saved.getAdmin().getUsername())
                .build();


        return null;
    }
}

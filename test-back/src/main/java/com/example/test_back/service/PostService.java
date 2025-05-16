package com.example.test_back.service;

import com.example.test_back.dto.request.PostCreateRequestDto;
import com.example.test_back.dto.request.PostUpdateRequestDto;
import com.example.test_back.dto.response.PostDetailResponseDto;
import com.example.test_back.dto.response.PostListResponseDto;
import com.example.test_back.dto.response.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    ResponseDto<PostDetailResponseDto> createPost(@Valid PostCreateRequestDto dto);

    ResponseDto<PostDetailResponseDto> getPostById(Long id);

    ResponseDto<List<PostListResponseDto>> getAllPosts();

    ResponseDto<PostDetailResponseDto> updatePost(Long id, @Valid PostUpdateRequestDto dto);

    ResponseDto<Void> deletePost(Long id);
}
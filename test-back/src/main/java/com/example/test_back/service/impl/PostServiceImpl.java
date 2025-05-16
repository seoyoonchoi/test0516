package com.example.test_back.service.impl;

import com.example.test_back.common.ResponseMessage;
import com.example.test_back.dto.request.PostCreateRequestDto;
import com.example.test_back.dto.request.PostUpdateRequestDto;
import com.example.test_back.dto.response.PostDetailResponseDto;
import com.example.test_back.dto.response.PostListResponseDto;
import com.example.test_back.dto.response.ResponseDto;
import com.example.test_back.entity.Post;
import com.example.test_back.repository.PostRepository;
import com.example.test_back.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public ResponseDto<PostDetailResponseDto> createPost(PostCreateRequestDto dto) {
        PostDetailResponseDto responseDto = null;

        Post newPost = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        Post saved = postRepository.save(newPost);

        responseDto = PostDetailResponseDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<PostDetailResponseDto> getPostById(Long id) {
        PostDetailResponseDto responseDto = null;

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_POST + id));

        responseDto = PostDetailResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<PostListResponseDto>> getAllPosts() {
        List<PostListResponseDto> responseDtos = null;

        List<Post> posts = postRepository.findAll();

        responseDtos = posts.stream()
                .map(post -> PostListResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .authorId(post.getAuthor().getId())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    @Transactional
    public ResponseDto<PostDetailResponseDto> updatePost(Long id, PostUpdateRequestDto dto) {
        PostDetailResponseDto responseDto = null;

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_POST + id));

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        Post updatedPost = postRepository.save(post);

        responseDto = PostDetailResponseDto.builder()
                .id(updatedPost.getId())
                .title(updatedPost.getTitle())
                .content(updatedPost.getContent())
                .authorId(post.getAuthor().getId())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<Void> deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_POST + id));
        postRepository.deleteById(id);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
    }
}
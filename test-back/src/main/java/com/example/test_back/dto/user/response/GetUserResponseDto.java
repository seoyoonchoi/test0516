package com.example.test_back.dto.user.response;

import com.example.test_back.entity.User;
import lombok.Getter;

@Getter
public class GetUserResponseDto {
    private Long id;
    private String username;


    public GetUserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
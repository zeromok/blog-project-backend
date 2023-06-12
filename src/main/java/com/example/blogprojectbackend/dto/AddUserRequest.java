package com.example.blogprojectbackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddUserRequest {
    private String email;
    private String password;

    public AddUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
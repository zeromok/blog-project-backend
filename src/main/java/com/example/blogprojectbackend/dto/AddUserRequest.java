package com.example.blogprojectbackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class AddUserRequest {

    private String email;
    private String password;

}

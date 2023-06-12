package com.example.blogprojectbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatedAccessTokenRequest {

    private String refreshToken;

}

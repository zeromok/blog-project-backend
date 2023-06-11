package com.example.blogprojectbackend.controller;

import com.example.blogprojectbackend.dto.AddUserRequest;
import com.example.blogprojectbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user")
    public String singup(AddUserRequest request) {
        userService.save(request);
        return "redirect:/login";
    }
}

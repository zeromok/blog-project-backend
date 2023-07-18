package com.example.blogprojectbackend.controller;

import org.apache.catalina.WebResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class UserViewController {

    @GetMapping( "/" )
    public String  init( ) {
        return "redirect:login";
    }

    @GetMapping("/login")
    public String login() {
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String singup() {
        return "signup";
    }
}

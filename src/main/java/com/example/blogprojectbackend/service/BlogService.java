package com.example.blogprojectbackend.service;

import com.example.blogprojectbackend.domain.Article;
import com.example.blogprojectbackend.dto.AddArticleRequest;
import com.example.blogprojectbackend.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

}

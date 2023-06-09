package com.example.blogprojectbackend.controller;

import com.example.blogprojectbackend.domain.Article;
import com.example.blogprojectbackend.dto.AddArticleRequest;
import com.example.blogprojectbackend.dto.ArticleResponse;
import com.example.blogprojectbackend.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BolgApiController {

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article saveArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticle() {
        List<ArticleResponse> articles = blogService.findAll().stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(articles);
    }

} // end

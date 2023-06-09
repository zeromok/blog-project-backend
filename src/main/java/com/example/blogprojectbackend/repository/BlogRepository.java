package com.example.blogprojectbackend.repository;

import com.example.blogprojectbackend.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}

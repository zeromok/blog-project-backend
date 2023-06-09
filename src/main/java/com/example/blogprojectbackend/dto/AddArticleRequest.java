package com.example.blogprojectbackend.dto;

import com.example.blogprojectbackend.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

    private String title;
    private String content;

    // 빌더 패턴을 사용해 dto 객체를 entity 로 만들어주는 메서드
    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }

}

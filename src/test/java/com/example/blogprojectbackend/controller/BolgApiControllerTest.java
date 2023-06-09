package com.example.blogprojectbackend.controller;

import com.example.blogprojectbackend.domain.Article;
import com.example.blogprojectbackend.dto.AddArticleRequest;
import com.example.blogprojectbackend.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.SQLOutput;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BolgApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach
    void beforeEach() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        blogRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    void addArticle() throws Exception {
        // * given

        // 기본 설정 값들
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        // request 값 설정
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // * when
        // request 전송 후 ResultActions 타입 반환
        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody));

        // * then
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();
        assertThat(articles).hasSize(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }


    @Test
    @Order(2)
    @DisplayName("addArticle: 블로그 글 목록 조회에 성공한다.")
    void findAllArticle() throws Exception {
        // * given

        // 기본 설정 값들
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        // 값 설정
        blogRepository.save(Article.builder().title("title").content("content").build());

        // * when
        // request 전송 후 ResultActions 타입 반환
        ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        // * then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].content").value(content));
    }

} // end
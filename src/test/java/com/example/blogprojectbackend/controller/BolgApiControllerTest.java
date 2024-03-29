package com.example.blogprojectbackend.controller;

import com.example.blogprojectbackend.domain.Article;
import com.example.blogprojectbackend.dto.AddArticleRequest;
import com.example.blogprojectbackend.dto.UpdateArticleRequest;
import com.example.blogprojectbackend.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

        /**
         * Given : 블로그 글 추가에 필요한 요청객체 만들기
         * */
        // 기본 설정 값들
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        // request 값 설정
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        /**
         * When : 블로그 글 추가 API에 요청을 보낸다.
         *        이때 요청 타입은 JSON 이며, given 절에서 미리 만들어둔 객체를 요청 본문으로 함께 보낸다.
         * @return ResultActions
         * */
        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody));

        /**
         * Then : 응답 코드가 201인지 확인
         *        전체 조회 후 크기가 1인지 확인
         *        실제로 저장된 데이터와 요청 값 비교
         * */
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

        /**
         * Given : 블로그 글을 저장합니다.
         * */
        // 기본 설정 값들
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        // 값 설정
        blogRepository.save(Article.builder().title("title").content("content").build());

        /**
         * When : 목록 조회 API 를 호출합니다.
         * @return ResultActions
         * */
        ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        /**
         * Then : 응답코드가 OK 인지 확인
         *        반환받은 값(JSON)중에 0번째 요소의 값들 저장된 값과 비교합니다.
         * */
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].content").value(content));
    }

    @Test
    @Order(3)
    @DisplayName("findArticle: 아이디를 통해 블로그 글 조회에 성공한다.")
    void findArticle() throws Exception {

        /**
         * Given: 블로그 글을 저장합니다.
         * */
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article saveArticle = blogRepository.save(Article.builder().title("title").content("content").build());

        /**
         * When: 저장한 블로그 글의 id 값으로 API 를 호출
         * */
        final ResultActions resultActions = mockMvc.perform(get(url, saveArticle.getId()));

        /**
         * Then: 응답코드가 OK 인지 확인
         *       반환받은 값들이 저장된 아이디의 매핑되는 값인지 확인합니다.
         * */
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content))
                .andDo(print());
//                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @Order(4)
    @DisplayName("deleteArticle: 아이디를 통해 블로그 글 삭제에 성공한다.")
    void deleteArticle() throws Exception {

        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        /**
         * Given: 블로그 글을 저장합니다.
         * */
        Article saveArticle = blogRepository.save(Article.builder().title("title").content("content").build());

        /**
         * When: 저장한 블로그 글의 id 값으로 삭제 API 를 호출
         *       응답코드가 OK 인지 확인
         * */
        mockMvc.perform(delete(url, saveArticle.getId()))
                .andExpect(status().isOk())
                .andDo(print());

        /**
         * Then: 블로그 글 리스트를 조회해 크기가 0인지 확인
         * */
        List<Article> articles = blogRepository.findAll();

        assertThat(articles).isEmpty();
    }

    @Test
    @Order(5)
    @DisplayName("updateArticle: 블로그 글 수정에 성공한다.")
    void updateArticle() throws Exception {
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        final String newTitle = "newTitle";
        final String newContent = "newContent";

        /**
         * Given: 블로그 글을 저장하고, 수정에 필요한 요청객체 만듦
         * */
        Article saveArticle = blogRepository.save(Article.builder().title("title").content("content").build());
        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        /**
         * When:
         *       응답코드가 OK 인지 확인
         * */
         ResultActions resultActions = mockMvc.perform(put(url, saveArticle.getId())
                 .contentType(MediaType.APPLICATION_JSON_VALUE)
                 .content(objectMapper.writeValueAsString(request))
                 .characterEncoding(StandardCharsets.UTF_8));


        /**
         * Then: 응답 상태가 200Ok 이인지 확인
         *       수정된 데이터를 받아와 수정될 값과 비교
         * */
        resultActions
                .andExpect(status().isOk())
                .andDo(print());

        Article article = blogRepository.findById(saveArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);

    }


} // end
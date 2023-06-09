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
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        blogRepository.save(Article.builder().title("title").content("content").build());

        /**
         * When: 저장한 블로그 글의 id 값으로 API 를 호출
         * */
        final ResultActions resultActions = mockMvc.perform(get(url, 1));

        /**
         * Then: 응답코드가 OK 인지 확인
         *       반환받은 값들이 저장된 아이디의 매핑되는 값인지 확인합니다.
         * */
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].content").value(content))
                .andDo(print());
//                .andReturn().getResponse().getContentAsString();
    }


} // end
package com.example.springbootjpastudy.course.jdbc;

import com.example.springbootjpastudy.course.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CourseRepository {

    private final JdbcTemplate jdbcTemplate;

    private static String CREATE_QUERY =
            """
            create table course (
                id bigint not null, 
                name varchar(255) not null,
                author varchar(255) not null,
                primary key (id)
            );
            """;
    private static String INSERT_QUERY =
            """
            insert into course (id, name, author)
            values(?, ?, ?);
            """;

    private static String DELETE_QUERY =
            """
            delete from course where id = ?;
            """;

    private static String SELECT_QUERY =
            """
            select * from course where id = ?;
            """;

    public void create() {
        jdbcTemplate.execute(CREATE_QUERY);
    }

    public void insert(Course course) {
        jdbcTemplate.update(INSERT_QUERY, course.getId(), course.getName(), course.getAuthor());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    // queryForObject : 특정 객체를 생성
    // 결과 매핑 해야함 -> ResultSet -> Bean = Row Mapper 가 해줌
    // Row Mapper : ResultSet 의 각 행을 특정 Bean 에 연결시켜줌
    public Course selectById(Long id) {
//        return jdbcTemplate.queryForObject(SELECT_QUERY, new BeanPropertyRowMapper<>(Course.class), id);
        return (Course) jdbcTemplate.queryForObject(SELECT_QUERY, new CustomRowMapper(), id);
    }

}

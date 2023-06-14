package com.example.springbootjpastudy.course.jdbc;

import com.example.springbootjpastudy.course.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String author = rs.getString("author");

        return new Course(id, name, author);
    }
}

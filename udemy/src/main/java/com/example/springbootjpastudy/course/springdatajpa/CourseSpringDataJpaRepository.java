package com.example.springbootjpastudy.course.springdatajpa;

import com.example.springbootjpastudy.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseSpringDataJpaRepository extends JpaRepository<Course, Long> {

    // Custom
    List<Course> findByAuthor(String author);

    List<Course> findByName(String name);
}

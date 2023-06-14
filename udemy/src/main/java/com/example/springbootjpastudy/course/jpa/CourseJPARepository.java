package com.example.springbootjpastudy.course.jpa;

import com.example.springbootjpastudy.course.Course;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Transactional
public class CourseJPARepository {

    private final EntityManager manager;

    public void insert(Course course) {
        manager.merge(course);
    }

    public Course findById(long id) {
        return manager.find(Course.class, id);
    }

    public void deleteById(long id) {
        Course course = manager.find(Course.class, id);
        manager.remove(course);
    }


}

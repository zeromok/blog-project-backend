package com.example.springbootjpastudy.course;

import com.example.springbootjpastudy.course.jpa.CourseJPARepository;
import com.example.springbootjpastudy.course.jdbc.CourseRepository;
import com.example.springbootjpastudy.course.springdatajpa.CourseSpringDataJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseCommandLineRunner implements CommandLineRunner {

    private final CourseRepository courseJdbcRepository;
    private final CourseJPARepository courseJPARepository;
    private final CourseSpringDataJpaRepository courseSpringDataJpaRepository;

    @Override
    public void run(String... args) throws Exception {
//        courseRepository.create();
//
//        courseRepository.insert(new Course(1, "NAME_1", "AUTHOR_1"));
//        courseRepository.insert(new Course(2, "NAME_2", "AUTHOR_2"));
//        courseRepository.insert(new Course(3, "NAME_3", "AUTHOR_3"));
//
//        courseRepository.deleteById(3L);
//
//        System.out.println(courseRepository.selectById(1L));

//        ---


//        courseJPARepository.insert(new Course(1, "NAME_1_JPA", "AUTHOR_1"));
//        courseJPARepository.insert(new Course(2, "NAME_2_JPA", "AUTHOR_2"));
//        courseJPARepository.insert(new Course(3, "NAME_3_JPA", "AUTHOR_3"));
//
//        courseJPARepository.deleteById(3L);
//
//        System.out.println(courseJPARepository.findById(1L));

//        ---

        courseSpringDataJpaRepository.save(new Course(1, "NAME_1_JPA", "AUTHOR_1"));
        courseSpringDataJpaRepository.save(new Course(2, "NAME_2_JPA", "AUTHOR_2"));
        courseSpringDataJpaRepository.save(new Course(3, "NAME_3_JPA", "AUTHOR_3"));

        courseSpringDataJpaRepository.deleteById(3L);

        System.out.println(courseSpringDataJpaRepository.findById(1L));
        System.out.println(courseSpringDataJpaRepository.findById(2L));

        // select * from course
        System.out.println(courseSpringDataJpaRepository.findAll());
        // select count(*)
        System.out.println(courseSpringDataJpaRepository.count());

        // Custom
        System.out.println(courseSpringDataJpaRepository.findByAuthor("AUTHOR_2"));
        System.out.println(courseSpringDataJpaRepository.findByAuthor(""));

        System.out.println(courseSpringDataJpaRepository.findByName(""));
        System.out.println(courseSpringDataJpaRepository.findByName("NAME_2_JPA"));





    }
}

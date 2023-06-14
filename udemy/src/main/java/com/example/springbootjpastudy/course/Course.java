package com.example.springbootjpastudy.course;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
//@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Course {

    @Id
    private long id;

//    @Column(name = "name")
    private String name;

//    @Column(name = "author")
    private String author;

}

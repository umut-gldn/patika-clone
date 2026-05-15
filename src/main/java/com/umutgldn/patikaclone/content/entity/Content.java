package com.umutgldn.patikaclone.content.entity;

import com.umutgldn.patikaclone.common.entity.BaseEntity;
import com.umutgldn.patikaclone.course.entity.Course;
import com.umutgldn.patikaclone.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "contents")
public class Content extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 500)
    private String youtubeLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;
}
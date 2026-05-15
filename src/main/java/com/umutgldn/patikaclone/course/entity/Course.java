package com.umutgldn.patikaclone.course.entity;



import com.umutgldn.patikaclone.common.entity.BaseEntity;
import com.umutgldn.patikaclone.path.entity.Path;
import com.umutgldn.patikaclone.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity 
@Table(name = "courses")
public class Course extends BaseEntity{

    @Column(nullable = false,length = 100)
    private String name;

    @Column(nullable = false,length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "path_id",nullable = false)
    private Path path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id",nullable = false)
    private User instructor;
    
}

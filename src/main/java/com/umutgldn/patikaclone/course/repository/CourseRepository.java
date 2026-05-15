package com.umutgldn.patikaclone.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umutgldn.patikaclone.course.entity.Course;

public interface CourseRepository extends JpaRepository<Course,Long> {
    boolean existsByNameIgnoreCase(String name);

}

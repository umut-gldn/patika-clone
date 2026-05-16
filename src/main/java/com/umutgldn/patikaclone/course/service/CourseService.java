package com.umutgldn.patikaclone.course.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.umutgldn.patikaclone.course.dto.CourseResponse;
import com.umutgldn.patikaclone.course.dto.CourseSaveRequest;

public interface CourseService {

    CourseResponse create(CourseSaveRequest request);

    Page<CourseResponse> getAll(Pageable pageable);

    CourseResponse getById(Long id);

    CourseResponse update(Long id, CourseSaveRequest request);

    void delete(Long id);
}

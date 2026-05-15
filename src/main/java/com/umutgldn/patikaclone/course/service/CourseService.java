package com.umutgldn.patikaclone.course.service;

import java.util.List;

import com.umutgldn.patikaclone.course.dto.CourseResponse;
import com.umutgldn.patikaclone.course.dto.CourseSaveRequest;

public interface CourseService {

    CourseResponse create(CourseSaveRequest request);

    List<CourseResponse> getAll();

    CourseResponse getById(Long id);

    CourseResponse update(Long id, CourseSaveRequest request);

    void delete(Long id);
}

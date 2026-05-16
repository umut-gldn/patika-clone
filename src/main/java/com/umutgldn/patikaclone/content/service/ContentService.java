package com.umutgldn.patikaclone.content.service;

import com.umutgldn.patikaclone.content.dto.ContentResponse;
import com.umutgldn.patikaclone.content.dto.ContentSaveRequest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentService {

    ContentResponse create(ContentSaveRequest request);

   Page<ContentResponse> getAll(Long courseId, String title, Pageable pageable);

    ContentResponse getById(Long id);

    ContentResponse update(Long id, ContentSaveRequest request);

    void delete(Long id);

    List<ContentResponse> getContentsForEnrolledCourse(Long courseId);
}
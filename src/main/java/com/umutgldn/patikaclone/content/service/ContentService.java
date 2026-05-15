package com.umutgldn.patikaclone.content.service;

import com.umutgldn.patikaclone.content.dto.ContentResponse;
import com.umutgldn.patikaclone.content.dto.ContentSaveRequest;

import java.util.List;

public interface ContentService {

    ContentResponse create(ContentSaveRequest request);

    List<ContentResponse> getAll(Long courseId, String title);

    ContentResponse getById(Long id);

    ContentResponse update(Long id, ContentSaveRequest request);

    void delete(Long id);
}
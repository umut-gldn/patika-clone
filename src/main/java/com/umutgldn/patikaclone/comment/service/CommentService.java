package com.umutgldn.patikaclone.comment.service;

import com.umutgldn.patikaclone.comment.dto.CommentResponse;
import com.umutgldn.patikaclone.comment.dto.CommentSaveRequest;

import java.util.List;

public interface CommentService {

    CommentResponse create(CommentSaveRequest request);

    List<CommentResponse> getByContent(Long contentId);

    void delete(Long id);
}
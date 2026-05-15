package com.umutgldn.patikaclone.comment.controller;

import com.umutgldn.patikaclone.comment.dto.CommentResponse;
import com.umutgldn.patikaclone.comment.dto.CommentSaveRequest;
import com.umutgldn.patikaclone.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('STUDENT')")
    public CommentResponse create(
            @RequestBody @Valid CommentSaveRequest request
    ) {
        return commentService.create(request);
    }

    @GetMapping("/content/{contentId}")
    public List<CommentResponse> getByContent(
            @PathVariable Long contentId
    ) {
        return commentService.getByContent(contentId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('STUDENT', 'OPERATOR')")
    public void delete(@PathVariable Long id) {
        commentService.delete(id);
    }
}
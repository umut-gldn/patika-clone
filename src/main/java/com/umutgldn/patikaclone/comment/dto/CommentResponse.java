package com.umutgldn.patikaclone.comment.dto;

public record CommentResponse(
        Long id,
        String text,
        Long studentId,
        String studentName,
        Long contentId,
        String contentTitle
) {
}
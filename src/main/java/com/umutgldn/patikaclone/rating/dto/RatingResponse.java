package com.umutgldn.patikaclone.rating.dto;

public record RatingResponse(
        Long id,
        Integer score,
        Long studentId,
        String studentName,
        Long contentId,
        String contentTitle
) {
}
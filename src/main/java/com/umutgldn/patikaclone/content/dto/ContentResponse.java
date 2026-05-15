package com.umutgldn.patikaclone.content.dto;

public record ContentResponse(
        Long id,
        String title,
        String description,
        String youtubeLink,
        Long courseId,
        String courseName,
        Long instructorId,
        String instructorName
) {
}
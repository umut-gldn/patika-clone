package com.umutgldn.patikaclone.enrollment.dto;

public record EnrollmentResponse(
        Long id,
        Long studentId,
        String studentName,
        Long courseId,
        String courseName
) {
}
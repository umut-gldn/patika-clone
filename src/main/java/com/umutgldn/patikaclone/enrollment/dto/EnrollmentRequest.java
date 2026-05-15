package com.umutgldn.patikaclone.enrollment.dto;

import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(
        @NotNull
        Long courseId
) {
}
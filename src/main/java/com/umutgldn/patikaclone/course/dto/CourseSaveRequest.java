package com.umutgldn.patikaclone.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseSaveRequest(
    @NotBlank
    @Size(max = 100)
    String name,

    @NotBlank
    @Size(max = 100)
    String description,

    @NotNull
    Long pathId,

    @NotNull
    Long instructorId
) {
}

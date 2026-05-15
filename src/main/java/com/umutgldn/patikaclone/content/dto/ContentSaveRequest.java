package com.umutgldn.patikaclone.content.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContentSaveRequest(

        @NotBlank
        @Size(max = 150)
        String title,

        @NotBlank
        @Size(max = 1000)
        String description,

        @NotBlank
        @Size(max = 500)
        String youtubeLink,

        @NotNull
        Long courseId
) {
}
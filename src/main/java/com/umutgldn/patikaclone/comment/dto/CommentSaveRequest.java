package com.umutgldn.patikaclone.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentSaveRequest(

        @NotBlank
        @Size(max = 1000)
        String text,

        @NotNull
        Long contentId
) {
}
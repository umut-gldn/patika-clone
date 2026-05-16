package com.umutgldn.patikaclone.quiz.dto;

import com.umutgldn.patikaclone.quiz.entity.QuizOption;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record QuizQuestionSaveRequest(

        @NotBlank
        @Size(max = 1000)
        String questionText,

        @NotBlank
        @Size(max = 300)
        String optionA,

        @NotBlank
        @Size(max = 300)
        String optionB,

        @NotBlank
        @Size(max = 300)
        String optionC,

        @NotBlank
        @Size(max = 300)
        String optionD,

        @NotNull
        QuizOption correctAnswer,

        @NotNull
        Long contentId
) {
}
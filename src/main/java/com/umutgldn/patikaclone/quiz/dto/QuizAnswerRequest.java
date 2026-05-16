package com.umutgldn.patikaclone.quiz.dto;

import com.umutgldn.patikaclone.quiz.entity.QuizOption;
import jakarta.validation.constraints.NotNull;

public record QuizAnswerRequest(

        @NotNull
        Long questionId,

        @NotNull
        QuizOption selectedAnswer
) {
}
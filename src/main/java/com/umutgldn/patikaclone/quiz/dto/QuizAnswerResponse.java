package com.umutgldn.patikaclone.quiz.dto;

import com.umutgldn.patikaclone.quiz.entity.QuizOption;

public record QuizAnswerResponse(
        Long questionId,
        QuizOption selectedAnswer,
        boolean correct
) {
}
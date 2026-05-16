package com.umutgldn.patikaclone.quiz.dto;

import com.umutgldn.patikaclone.quiz.entity.QuizOption;

public record QuizQuestionResponse(
        Long id,
        String questionText,
        String optionA,
        String optionB,
        String optionC,
        String optionD,
        QuizOption correctAnswer,
        Long contentId,
        String contentTitle
) {
}
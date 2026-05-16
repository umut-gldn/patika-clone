package com.umutgldn.patikaclone.quiz.dto;

public record StudentQuizQuestionResponse(
        Long id,
        String questionText,
        String optionA,
        String optionB,
        String optionC,
        String optionD,
        Long contentId,
        String contentTitle
) {
}
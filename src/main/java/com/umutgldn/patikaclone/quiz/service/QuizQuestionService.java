package com.umutgldn.patikaclone.quiz.service;

import com.umutgldn.patikaclone.quiz.dto.QuizAnswerRequest;
import com.umutgldn.patikaclone.quiz.dto.QuizAnswerResponse;
import com.umutgldn.patikaclone.quiz.dto.QuizQuestionResponse;
import com.umutgldn.patikaclone.quiz.dto.QuizQuestionSaveRequest;
import com.umutgldn.patikaclone.quiz.dto.StudentQuizQuestionResponse;

import java.util.List;

public interface QuizQuestionService {

    QuizQuestionResponse create(QuizQuestionSaveRequest request);

    List<QuizQuestionResponse> getAll();

    List<QuizQuestionResponse> getByContent(Long contentId);

    QuizQuestionResponse getById(Long id);

    QuizQuestionResponse update(Long id, QuizQuestionSaveRequest request);

    void delete(Long id);

    List<StudentQuizQuestionResponse> getStudentQuestionsByContent(Long contentId);

    QuizAnswerResponse answerQuestion(QuizAnswerRequest request);
}
package com.umutgldn.patikaclone.quiz.controller;

import com.umutgldn.patikaclone.quiz.dto.QuizAnswerRequest;
import com.umutgldn.patikaclone.quiz.dto.QuizAnswerResponse;
import com.umutgldn.patikaclone.quiz.dto.QuizQuestionResponse;
import com.umutgldn.patikaclone.quiz.dto.QuizQuestionSaveRequest;
import com.umutgldn.patikaclone.quiz.dto.StudentQuizQuestionResponse;
import com.umutgldn.patikaclone.quiz.service.QuizQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz-questions")
@RequiredArgsConstructor
public class QuizQuestionController {

    private final QuizQuestionService quizQuestionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('OPERATOR', 'INSTRUCTOR')")
    public QuizQuestionResponse create(@RequestBody @Valid QuizQuestionSaveRequest request) {
        return quizQuestionService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'INSTRUCTOR')")
    public List<QuizQuestionResponse> getAll() {
        return quizQuestionService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'INSTRUCTOR')")
    public QuizQuestionResponse getById(@PathVariable Long id) {
        return quizQuestionService.getById(id);
    }

    @GetMapping("/content/{contentId}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'INSTRUCTOR')")
    public List<QuizQuestionResponse> getByContent(@PathVariable Long contentId) {
        return quizQuestionService.getByContent(contentId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'INSTRUCTOR')")
    public QuizQuestionResponse update(
            @PathVariable Long id,
            @RequestBody @Valid QuizQuestionSaveRequest request) {
        return quizQuestionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('OPERATOR', 'INSTRUCTOR')")
    public void delete(@PathVariable Long id) {
        quizQuestionService.delete(id);
    }

    @GetMapping("/student/content/{contentId}")
    @PreAuthorize("hasRole('STUDENT')")
    public List<StudentQuizQuestionResponse> getStudentQuestionsByContent(
            @PathVariable Long contentId) {
        return quizQuestionService.getStudentQuestionsByContent(contentId);
    }

    @PostMapping("/student/answer")
    @PreAuthorize("hasRole('STUDENT')")
    public QuizAnswerResponse answerQuestion(
            @RequestBody @Valid QuizAnswerRequest request) {
        return quizQuestionService.answerQuestion(request);
    }
}
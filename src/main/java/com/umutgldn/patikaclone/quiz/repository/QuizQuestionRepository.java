package com.umutgldn.patikaclone.quiz.repository;

import com.umutgldn.patikaclone.quiz.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    List<QuizQuestion> findByContentId(Long contentId);
}
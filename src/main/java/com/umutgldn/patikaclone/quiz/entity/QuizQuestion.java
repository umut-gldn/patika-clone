package com.umutgldn.patikaclone.quiz.entity;

import com.umutgldn.patikaclone.common.entity.BaseEntity;
import com.umutgldn.patikaclone.content.entity.Content;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "quiz_questions")
public class QuizQuestion extends BaseEntity {

    @Column(nullable = false, length = 1000)
    private String questionText;

    @Column(nullable = false, length = 300)
    private String optionA;

    @Column(nullable = false, length = 300)
    private String optionB;

    @Column(nullable = false, length = 300)
    private String optionC;

    @Column(nullable = false, length = 300)
    private String optionD;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private QuizOption correctAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;
}
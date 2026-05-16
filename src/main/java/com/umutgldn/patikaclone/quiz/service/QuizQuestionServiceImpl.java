package com.umutgldn.patikaclone.quiz.service;

import com.umutgldn.patikaclone.common.exception.BusinessException;
import com.umutgldn.patikaclone.common.security.CurrentUserService;
import com.umutgldn.patikaclone.content.entity.Content;
import com.umutgldn.patikaclone.content.repository.ContentRepository;
import com.umutgldn.patikaclone.enrollment.repository.EnrollmentRepository;
import com.umutgldn.patikaclone.quiz.dto.QuizAnswerRequest;
import com.umutgldn.patikaclone.quiz.dto.QuizAnswerResponse;
import com.umutgldn.patikaclone.quiz.dto.QuizQuestionResponse;
import com.umutgldn.patikaclone.quiz.dto.QuizQuestionSaveRequest;
import com.umutgldn.patikaclone.quiz.dto.StudentQuizQuestionResponse;
import com.umutgldn.patikaclone.quiz.entity.QuizQuestion;
import com.umutgldn.patikaclone.quiz.repository.QuizQuestionRepository;
import com.umutgldn.patikaclone.user.entity.User;
import com.umutgldn.patikaclone.user.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizQuestionServiceImpl implements QuizQuestionService {

    private final QuizQuestionRepository quizQuestionRepository;
    private final ContentRepository contentRepository;
    private final CurrentUserService currentUserService;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public QuizQuestionResponse create(QuizQuestionSaveRequest request) {
        User currentUser = currentUserService.getCurrentUser();
        Content content = findContentById(request.contentId());

        validateInstructorCanManageContent(currentUser, content);

        QuizQuestion question = new QuizQuestion();
        question.setQuestionText(request.questionText());
        question.setOptionA(request.optionA());
        question.setOptionB(request.optionB());
        question.setOptionC(request.optionC());
        question.setOptionD(request.optionD());
        question.setCorrectAnswer(request.correctAnswer());
        question.setContent(content);

        quizQuestionRepository.save(question);

        return toResponse(question);
    }

    @Override
    public List<QuizQuestionResponse> getAll() {
        return quizQuestionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<QuizQuestionResponse> getByContent(Long contentId) {
        return quizQuestionRepository.findByContentId(contentId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public QuizQuestionResponse getById(Long id) {
        QuizQuestion question = findQuestionById(id);
        return toResponse(question);
    }

    @Override
    public QuizQuestionResponse update(Long id, QuizQuestionSaveRequest request) {
        User currentUser = currentUserService.getCurrentUser();
        QuizQuestion question = findQuestionById(id);
        Content content = findContentById(request.contentId());

        validateInstructorCanManageContent(currentUser, question.getContent());
        validateInstructorCanManageContent(currentUser, content);

        question.setQuestionText(request.questionText());
        question.setOptionA(request.optionA());
        question.setOptionB(request.optionB());
        question.setOptionC(request.optionC());
        question.setOptionD(request.optionD());
        question.setCorrectAnswer(request.correctAnswer());
        question.setContent(content);

        quizQuestionRepository.save(question);

        return toResponse(question);
    }

    @Override
    public void delete(Long id) {
        User currentUser = currentUserService.getCurrentUser();
        QuizQuestion question = findQuestionById(id);

        validateInstructorCanManageContent(currentUser, question.getContent());

        quizQuestionRepository.delete(question);
    }

    private Content findContentById(Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("İçerik bulunamadı"));
    }

    private QuizQuestion findQuestionById(Long id) {
        return quizQuestionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Quiz sorusu bulunamadı"));
    }

    private void validateInstructorCanManageContent(User user, Content content) {
        if (user.getRole() == Role.OPERATOR) {
            return;
        }

        if (user.getRole() != Role.INSTRUCTOR) {
            throw new BusinessException("Bu işlem sadece eğitmen veya operatör tarafından yapılabilir");
        }

        Long courseInstructorId = content.getCourse().getInstructor().getId();

        if (!courseInstructorId.equals(user.getId())) {
            throw new BusinessException("Bu içeriğe quiz sorusu ekleme/düzenleme yetkiniz yok");
        }
    }

    private QuizQuestionResponse toResponse(QuizQuestion question) {
        return new QuizQuestionResponse(
                question.getId(),
                question.getQuestionText(),
                question.getOptionA(),
                question.getOptionB(),
                question.getOptionC(),
                question.getOptionD(),
                question.getCorrectAnswer(),
                question.getContent().getId(),
                question.getContent().getTitle());
    }

    @Override
    public List<StudentQuizQuestionResponse> getStudentQuestionsByContent(Long contentId) {
        User currentUser = currentUserService.getCurrentUser();

        if (currentUser.getRole() != Role.STUDENT) {
            throw new BusinessException("Bu işlem sadece öğrenciler tarafından yapılabilir");
        }

        Content content = findContentById(contentId);

        validateStudentEnrolledToContentCourse(currentUser, content);

        return quizQuestionRepository.findByContentId(contentId)
                .stream()
                .map(this::toStudentResponse)
                .toList();
    }

    @Override
    public QuizAnswerResponse answerQuestion(QuizAnswerRequest request) {
        User currentUser = currentUserService.getCurrentUser();

        if (currentUser.getRole() != Role.STUDENT) {
            throw new BusinessException("Bu işlem sadece öğrenciler tarafından yapılabilir");
        }

        QuizQuestion question = findQuestionById(request.questionId());

        validateStudentEnrolledToContentCourse(currentUser, question.getContent());

        boolean correct = question.getCorrectAnswer() == request.selectedAnswer();

        return new QuizAnswerResponse(
                question.getId(),
                request.selectedAnswer(),
                correct);
    }

    private void validateStudentEnrolledToContentCourse(User student, Content content) {
        Long courseId = content.getCourse().getId();

        boolean enrolled = enrollmentRepository.existsByStudentIdAndCourseId(
                student.getId(),
                courseId);

        if (!enrolled) {
            throw new BusinessException("Bu içeriğin bağlı olduğu eğitime kayıtlı değilsiniz");
        }
    }

    private StudentQuizQuestionResponse toStudentResponse(QuizQuestion question) {
        return new StudentQuizQuestionResponse(
                question.getId(),
                question.getQuestionText(),
                question.getOptionA(),
                question.getOptionB(),
                question.getOptionC(),
                question.getOptionD(),
                question.getContent().getId(),
                question.getContent().getTitle());
    }

}
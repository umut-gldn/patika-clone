package com.umutgldn.patikaclone.content.service;

import com.umutgldn.patikaclone.common.exception.BusinessException;
import com.umutgldn.patikaclone.common.security.CurrentUserService;
import com.umutgldn.patikaclone.content.dto.ContentResponse;
import com.umutgldn.patikaclone.content.dto.ContentSaveRequest;
import com.umutgldn.patikaclone.content.entity.Content;
import com.umutgldn.patikaclone.content.repository.ContentRepository;
import com.umutgldn.patikaclone.course.entity.Course;
import com.umutgldn.patikaclone.course.repository.CourseRepository;
import com.umutgldn.patikaclone.enrollment.repository.EnrollmentRepository;
import com.umutgldn.patikaclone.user.entity.User;
import com.umutgldn.patikaclone.user.enums.Role;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;
    private final CourseRepository courseRepository;
    private final CurrentUserService currentUserService;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public ContentResponse create(ContentSaveRequest request) {
        User currentUser = currentUserService.getCurrentUser();
        Course course = findCourseById(request.courseId());

        validateInstructorCanManageCourse(currentUser, course);

        Content content = new Content();
        content.setTitle(request.title());
        content.setDescription(request.description());
        content.setYoutubeLink(request.youtubeLink());
        content.setCourse(course);
        content.setInstructor(currentUser);

        contentRepository.save(content);

        return toResponse(content);
    }

    @Override
    public Page<ContentResponse> getAll(Long courseId, String title, Pageable pageable) {
        Page<Content> contents;

        if (courseId != null && title != null && !title.isBlank()) {
            contents = contentRepository.findByCourseIdAndTitleContainingIgnoreCase(courseId, title, pageable);
        } else if (courseId != null) {
            contents = contentRepository.findByCourseId(courseId, pageable);
        } else if (title != null && !title.isBlank()) {
            contents = contentRepository.findByTitleContainingIgnoreCase(title, pageable);
        } else {
            contents = contentRepository.findAll(pageable);
        }

        return contents.map(this::toResponse);
    }

    @Override
    public ContentResponse getById(Long id) {
        Content content = findContentById(id);
        return toResponse(content);
    }

    @Override
    public ContentResponse update(Long id, ContentSaveRequest request) {
        User currentUser = currentUserService.getCurrentUser();
        Content content = findContentById(id);
        Course course = findCourseById(request.courseId());

        validateContentOwnerOrOperator(currentUser, content);
        validateInstructorCanManageCourse(currentUser, course);

        content.setTitle(request.title());
        content.setDescription(request.description());
        content.setYoutubeLink(request.youtubeLink());
        content.setCourse(course);

        contentRepository.save(content);

        return toResponse(content);
    }

    @Override
    public void delete(Long id) {
        User currentUser = currentUserService.getCurrentUser();
        Content content = findContentById(id);

        validateContentOwnerOrOperator(currentUser, content);

        contentRepository.delete(content);
    }

    @Override
    public List<ContentResponse> getContentsForEnrolledCourse(Long courseId) {
        User currentUser = currentUserService.getCurrentUser();

        if (currentUser.getRole() != Role.STUDENT) {
            throw new BusinessException("Bu işlem sadece öğrenciler tarafından yapılabilir");
        }

        boolean enrolled = enrollmentRepository.existsByStudentIdAndCourseId(
                currentUser.getId(),
                courseId);

        if (!enrolled) {
            throw new BusinessException("Bu eğitime kayıtlı değilsiniz");
        }

        return contentRepository.findByCourseId(courseId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private Course findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Eğitim bulunamadı"));
    }

    private Content findContentById(Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("İçerik bulunamadı"));
    }

    private void validateInstructorCanManageCourse(User user, Course course) {
        if (user.getRole() == Role.OPERATOR) {
            return;
        }

        if (user.getRole() != Role.INSTRUCTOR) {
            throw new BusinessException("Bu işlem sadece eğitmen veya operatör tarafından yapılabilir");
        }

        if (!course.getInstructor().getId().equals(user.getId())) {
            throw new BusinessException("Bu eğitime içerik ekleme yetkiniz yok");
        }
    }

    private void validateContentOwnerOrOperator(User user, Content content) {
        if (user.getRole() == Role.OPERATOR) {
            return;
        }

        if (!content.getInstructor().getId().equals(user.getId())) {
            throw new BusinessException("Bu içeriği düzenleme/silme yetkiniz yok");
        }
    }

    private ContentResponse toResponse(Content content) {
        return new ContentResponse(
                content.getId(),
                content.getTitle(),
                content.getDescription(),
                content.getYoutubeLink(),
                content.getCourse().getId(),
                content.getCourse().getName(),
                content.getInstructor().getId(),
                content.getInstructor().getFullName());
    }
}
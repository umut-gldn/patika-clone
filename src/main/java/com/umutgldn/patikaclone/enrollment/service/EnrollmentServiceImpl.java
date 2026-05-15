package com.umutgldn.patikaclone.enrollment.service;

import com.umutgldn.patikaclone.common.exception.BusinessException;
import com.umutgldn.patikaclone.common.security.CurrentUserService;
import com.umutgldn.patikaclone.course.entity.Course;
import com.umutgldn.patikaclone.course.repository.CourseRepository;
import com.umutgldn.patikaclone.enrollment.dto.EnrollmentRequest;
import com.umutgldn.patikaclone.enrollment.dto.EnrollmentResponse;
import com.umutgldn.patikaclone.enrollment.entity.Enrollment;
import com.umutgldn.patikaclone.enrollment.repository.EnrollmentRepository;
import com.umutgldn.patikaclone.user.entity.User;
import com.umutgldn.patikaclone.user.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final CurrentUserService currentUserService;

    @Override
    public EnrollmentResponse enroll(EnrollmentRequest request) {
        User currentUser = currentUserService.getCurrentUser();

        if (currentUser.getRole() != Role.STUDENT) {
            throw new BusinessException("Sadece öğrenciler derse kayıt olabilir");
        }

        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new BusinessException("Eğitim bulunamadı"));

        if (enrollmentRepository.existsByStudentIdAndCourseId(currentUser.getId(), course.getId())) {
            throw new BusinessException("Bu eğitime zaten kayıtlısınız");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(currentUser);
        enrollment.setCourse(course);

        enrollmentRepository.save(enrollment);

        return toResponse(enrollment);
    }

    @Override
    public List<EnrollmentResponse> getMyEnrollments() {
        User currentUser = currentUserService.getCurrentUser();

        return enrollmentRepository.findByStudentId(currentUser.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private EnrollmentResponse toResponse(Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.getId(),
                enrollment.getStudent().getId(),
                enrollment.getStudent().getFullName(),
                enrollment.getCourse().getId(),
                enrollment.getCourse().getName());
    }
}
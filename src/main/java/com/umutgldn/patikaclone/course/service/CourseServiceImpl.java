package com.umutgldn.patikaclone.course.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.umutgldn.patikaclone.common.exception.BusinessException;
import com.umutgldn.patikaclone.course.dto.CourseResponse;
import com.umutgldn.patikaclone.course.dto.CourseSaveRequest;
import com.umutgldn.patikaclone.course.entity.Course;
import com.umutgldn.patikaclone.course.repository.CourseRepository;
import com.umutgldn.patikaclone.path.entity.Path;
import com.umutgldn.patikaclone.path.repository.PathRepository;
import com.umutgldn.patikaclone.user.entity.User;
import com.umutgldn.patikaclone.user.enums.Role;
import com.umutgldn.patikaclone.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final PathRepository pathRepository;
    private final UserRepository userRepository;

    @Override
    public CourseResponse create(CourseSaveRequest request) {
        if (courseRepository.existsByNameIgnoreCase(request.name())) {
            throw new BusinessException("Bu eğitim zaten mevcut");
        }

        Path path = findPathById(request.pathId());
        User instructor = findInstructorById(request.instructorId());

        Course course = new Course();
        course.setName(request.name());
        course.setDescription(request.description());
        course.setPath(path);
        course.setInstructor(instructor);

        courseRepository.save(course);

        return toResponse(course);
    }

    @Override
    public Page<CourseResponse> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Override
    public CourseResponse getById(Long id) {
        Course course = findCourseById(id);
        return toResponse(course);
    }

    @Override
    public CourseResponse update(Long id, CourseSaveRequest request) {
        Course course = findCourseById(id);

        if (!course.getName().equalsIgnoreCase(request.name())
                && courseRepository.existsByNameIgnoreCase(request.name())) {
            throw new BusinessException("Bu eğitim zaten mevcut");
        }
        Path path = findPathById(request.pathId());
        User instructor = findInstructorById(request.instructorId());

        course.setName(request.name());
        course.setDescription(request.description());
        course.setPath(path);
        course.setInstructor(instructor);

        courseRepository.save(course);

        return toResponse(course);
    }

    @Override
    public void delete(Long id) {
        Course course = findCourseById(id);
        courseRepository.delete(course);
    }

    private Course findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Eğitim bulunamadı"));
    }

    private Path findPathById(Long id) {
        return pathRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Patika bulunamadı"));
    }

    private User findInstructorById(Long id) {
        User instructor = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Eğitmen bulunamadı"));
        if (instructor.getRole() != Role.INSTRUCTOR) {
            throw new BusinessException("Bu kullanıcı eğitmen değil");
        }
        return instructor;
    }

    private CourseResponse toResponse(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getPath().getId(),
                course.getPath().getName(),
                course.getInstructor().getId(),
                course.getInstructor().getFullName());
    }
}

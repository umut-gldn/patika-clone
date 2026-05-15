package com.umutgldn.patikaclone.enrollment.controller;

import com.umutgldn.patikaclone.enrollment.dto.EnrollmentRequest;
import com.umutgldn.patikaclone.enrollment.dto.EnrollmentResponse;
import com.umutgldn.patikaclone.enrollment.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('STUDENT')")
    public EnrollmentResponse enroll(@RequestBody @Valid EnrollmentRequest request) {
        return enrollmentService.enroll(request);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public List<EnrollmentResponse> getMyEnrollments() {
        return enrollmentService.getMyEnrollments();
    }
}
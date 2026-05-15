package com.umutgldn.patikaclone.enrollment.service;

import com.umutgldn.patikaclone.enrollment.dto.EnrollmentRequest;
import com.umutgldn.patikaclone.enrollment.dto.EnrollmentResponse;

import java.util.List;

public interface EnrollmentService {

    EnrollmentResponse enroll(EnrollmentRequest request);

    List<EnrollmentResponse> getMyEnrollments();
}
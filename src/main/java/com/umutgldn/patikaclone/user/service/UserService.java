package com.umutgldn.patikaclone.user.service;

import com.umutgldn.patikaclone.user.dto.InstructorCreateRequest;
import com.umutgldn.patikaclone.user.dto.UserResponse;

public interface UserService {

    UserResponse createInstructor(InstructorCreateRequest request);

}

package com.umutgldn.patikaclone.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.umutgldn.patikaclone.user.dto.InstructorCreateRequest;
import com.umutgldn.patikaclone.user.dto.UserResponse;
import com.umutgldn.patikaclone.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/instructors")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('OPERATOR')")
    public UserResponse createInstructor(
            @RequestBody @Valid InstructorCreateRequest request) {
        return userService.createInstructor(request);
    }
}

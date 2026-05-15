package com.umutgldn.patikaclone.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InstructorCreateRequest(
    @NotBlank
    String fullName,

    @Email
    @NotBlank
    String email,

    @NotBlank
    String password
) {
}

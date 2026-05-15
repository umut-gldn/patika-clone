package com.umutgldn.patikaclone.user.dto;

import com.umutgldn.patikaclone.user.enums.Role;

public record UserResponse(
    Long id,
    String fullName,
    String email,
    Role role
) {

}

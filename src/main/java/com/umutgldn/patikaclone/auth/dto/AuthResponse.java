package com.umutgldn.patikaclone.auth.dto;

public record AuthResponse(
    String token,
    String tokenType
) {
}

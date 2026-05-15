package com.umutgldn.patikaclone.path.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PathSaveRequest(
    @NotBlank
    @Size(max = 100)
    String name
) {
}

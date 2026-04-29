package com.umutgldn.patikaclone.auth.service;

import com.umutgldn.patikaclone.auth.dto.AuthResponse;
import com.umutgldn.patikaclone.auth.dto.LoginRequest;
import com.umutgldn.patikaclone.auth.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}

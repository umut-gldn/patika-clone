package com.umutgldn.patikaclone.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.umutgldn.patikaclone.common.exception.BusinessException;
import com.umutgldn.patikaclone.auth.dto.AuthResponse;
import com.umutgldn.patikaclone.auth.dto.LoginRequest;
import com.umutgldn.patikaclone.auth.dto.RegisterRequest;
import com.umutgldn.patikaclone.user.entity.User;
import com.umutgldn.patikaclone.user.enums.Role;
import com.umutgldn.patikaclone.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email zaten kayıtlı");
        }
        User user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.STUDENT);

        userRepository.save(user);

        return new AuthResponse("Kayıt Başarılı");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException("Kullanıcı bulunamadı"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException("Şifre hatalı");
        }
        return new AuthResponse("Giriş Başarılı");
    }

}

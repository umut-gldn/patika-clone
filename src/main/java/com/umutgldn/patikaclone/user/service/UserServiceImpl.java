package com.umutgldn.patikaclone.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.umutgldn.patikaclone.common.exception.BusinessException;
import com.umutgldn.patikaclone.user.dto.InstructorCreateRequest;
import com.umutgldn.patikaclone.user.dto.UserResponse;
import com.umutgldn.patikaclone.user.entity.User;
import com.umutgldn.patikaclone.user.enums.Role;
import com.umutgldn.patikaclone.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserResponse createInstructor(InstructorCreateRequest request) {
        
        if(userRepository.existsByEmail(request.email())){
            throw new BusinessException("Email zaten kayıtlı");
        }
        User instructor= new User();
        instructor.setFullName(request.fullName());
        instructor.setEmail(request.email());
        instructor.setPassword(passwordEncoder.encode(request.password()));
        instructor.setRole(Role.INSTRUCTOR);

        userRepository.save(instructor);

        return toResponse(instructor);
    }
      private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );
    }

}

package com.umutgldn.patikaclone.common.security;

import com.umutgldn.patikaclone.common.exception.BusinessException;
import com.umutgldn.patikaclone.user.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (!(principal instanceof User user)) {
            throw new BusinessException("Kullanıcı bilgisi alınamadı");
        }

        return user;
    }
}
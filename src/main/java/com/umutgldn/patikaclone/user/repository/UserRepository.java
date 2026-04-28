package com.umutgldn.patikaclone.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umutgldn.patikaclone.user.entity.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User>findByEmail(String email);

    boolean existsByEmail(String email);
}

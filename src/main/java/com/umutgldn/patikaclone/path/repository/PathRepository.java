package com.umutgldn.patikaclone.path.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umutgldn.patikaclone.path.entity.Path;

public interface PathRepository extends JpaRepository<Path, Long> {

    boolean existsByNameIgnoreCase(String name);
}

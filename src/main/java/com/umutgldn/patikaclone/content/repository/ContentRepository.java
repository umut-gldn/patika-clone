package com.umutgldn.patikaclone.content.repository;

import com.umutgldn.patikaclone.content.entity.Content;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {

    List<Content> findByCourseId(Long courseId);

    Page<Content> findByCourseId(Long courseId, Pageable pageable);

    Page<Content> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Content> findByCourseIdAndTitleContainingIgnoreCase(Long courseId, String title, Pageable pageable);
}
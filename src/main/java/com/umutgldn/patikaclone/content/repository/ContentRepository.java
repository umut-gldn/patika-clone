package com.umutgldn.patikaclone.content.repository;

import com.umutgldn.patikaclone.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {

    List<Content> findByCourseId(Long courseId);

    List<Content> findByTitleContainingIgnoreCase(String title);

    List<Content> findByCourseIdAndTitleContainingIgnoreCase(Long courseId, String title);
}
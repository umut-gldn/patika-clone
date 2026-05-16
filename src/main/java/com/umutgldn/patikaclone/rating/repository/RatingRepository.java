package com.umutgldn.patikaclone.rating.repository;

import com.umutgldn.patikaclone.rating.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByStudentIdAndContentId(Long studentId, Long contentId);

    List<Rating> findByContentId(Long contentId);
}
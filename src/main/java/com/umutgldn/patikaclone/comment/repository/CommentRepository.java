package com.umutgldn.patikaclone.comment.repository;

import com.umutgldn.patikaclone.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByContentId(Long contentId);
}
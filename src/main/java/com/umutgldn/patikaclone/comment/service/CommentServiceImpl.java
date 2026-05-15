package com.umutgldn.patikaclone.comment.service;

import com.umutgldn.patikaclone.comment.dto.CommentResponse;
import com.umutgldn.patikaclone.comment.dto.CommentSaveRequest;
import com.umutgldn.patikaclone.comment.entity.Comment;
import com.umutgldn.patikaclone.comment.repository.CommentRepository;
import com.umutgldn.patikaclone.common.exception.BusinessException;
import com.umutgldn.patikaclone.common.security.CurrentUserService;
import com.umutgldn.patikaclone.content.entity.Content;
import com.umutgldn.patikaclone.content.repository.ContentRepository;
import com.umutgldn.patikaclone.user.entity.User;
import com.umutgldn.patikaclone.user.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ContentRepository contentRepository;
    private final CurrentUserService currentUserService;

    @Override
    public CommentResponse create(CommentSaveRequest request) {
        User currentUser = currentUserService.getCurrentUser();

        if (currentUser.getRole() != Role.STUDENT) {
            throw new BusinessException("Sadece öğrenciler yorum yapabilir");
        }

        Content content = contentRepository.findById(request.contentId())
                .orElseThrow(() -> new BusinessException("İçerik bulunamadı"));

        Comment comment = new Comment();
        comment.setText(request.text());
        comment.setStudent(currentUser);
        comment.setContent(content);

        commentRepository.save(comment);

        return toResponse(comment);
    }

    @Override
    public List<CommentResponse> getByContent(Long contentId) {
        return commentRepository.findByContentId(contentId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        User currentUser = currentUserService.getCurrentUser();

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Yorum bulunamadı"));

        boolean isOwner = comment.getStudent().getId().equals(currentUser.getId());
        boolean isOperator = currentUser.getRole() == Role.OPERATOR;

        if (!isOwner && !isOperator) {
            throw new BusinessException("Bu yorumu silme yetkiniz yok");
        }

        commentRepository.delete(comment);
    }

    private CommentResponse toResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getText(),
                comment.getStudent().getId(),
                comment.getStudent().getFullName(),
                comment.getContent().getId(),
                comment.getContent().getTitle()
        );
    }
}
package com.umutgldn.patikaclone.rating.service;

import com.umutgldn.patikaclone.common.exception.BusinessException;
import com.umutgldn.patikaclone.common.security.CurrentUserService;
import com.umutgldn.patikaclone.content.entity.Content;
import com.umutgldn.patikaclone.content.repository.ContentRepository;
import com.umutgldn.patikaclone.enrollment.repository.EnrollmentRepository;
import com.umutgldn.patikaclone.rating.dto.RatingResponse;
import com.umutgldn.patikaclone.rating.dto.RatingSaveRequest;
import com.umutgldn.patikaclone.rating.entity.Rating;
import com.umutgldn.patikaclone.rating.repository.RatingRepository;
import com.umutgldn.patikaclone.user.entity.User;
import com.umutgldn.patikaclone.user.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ContentRepository contentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CurrentUserService currentUserService;

    @Override
    public RatingResponse rate(RatingSaveRequest request) {
        User currentUser = currentUserService.getCurrentUser();

        if (currentUser.getRole() != Role.STUDENT) {
            throw new BusinessException("Sadece öğrenciler değerlendirme yapabilir");
        }

        Content content = contentRepository.findById(request.contentId())
                .orElseThrow(() -> new BusinessException("İçerik bulunamadı"));

        boolean enrolled = enrollmentRepository.existsByStudentIdAndCourseId(
                currentUser.getId(),
                content.getCourse().getId()
        );

        if (!enrolled) {
            throw new BusinessException("Bu içeriği değerlendirmek için eğitime kayıtlı olmalısınız");
        }

        Rating rating = ratingRepository
                .findByStudentIdAndContentId(currentUser.getId(), content.getId())
                .orElseGet(Rating::new);

        rating.setStudent(currentUser);
        rating.setContent(content);
        rating.setScore(request.score());

        ratingRepository.save(rating);

        return toResponse(rating);
    }

    @Override
    public List<RatingResponse> getByContent(Long contentId) {
        return ratingRepository.findByContentId(contentId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private RatingResponse toResponse(Rating rating) {
        return new RatingResponse(
                rating.getId(),
                rating.getScore(),
                rating.getStudent().getId(),
                rating.getStudent().getFullName(),
                rating.getContent().getId(),
                rating.getContent().getTitle()
        );
    }
}
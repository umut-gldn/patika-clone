package com.umutgldn.patikaclone.rating.controller;

import com.umutgldn.patikaclone.rating.dto.RatingResponse;
import com.umutgldn.patikaclone.rating.dto.RatingSaveRequest;
import com.umutgldn.patikaclone.rating.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('STUDENT')")
    public RatingResponse rate(@RequestBody @Valid RatingSaveRequest request) {
        return ratingService.rate(request);
    }

    @GetMapping("/content/{contentId}")
    public List<RatingResponse> getByContent(@PathVariable Long contentId) {
        return ratingService.getByContent(contentId);
    }
}
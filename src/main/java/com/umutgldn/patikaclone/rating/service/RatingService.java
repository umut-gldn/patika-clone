package com.umutgldn.patikaclone.rating.service;

import com.umutgldn.patikaclone.rating.dto.RatingResponse;
import com.umutgldn.patikaclone.rating.dto.RatingSaveRequest;

import java.util.List;

public interface RatingService {

    RatingResponse rate(RatingSaveRequest request);

    List<RatingResponse> getByContent(Long contentId);
}
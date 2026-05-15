package com.umutgldn.patikaclone.content.controller;

import com.umutgldn.patikaclone.content.dto.ContentResponse;
import com.umutgldn.patikaclone.content.dto.ContentSaveRequest;
import com.umutgldn.patikaclone.content.service.ContentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('OPERATOR', 'INSTRUCTOR')")
    public ContentResponse create(@RequestBody @Valid ContentSaveRequest request) {
        return contentService.create(request);
    }

    @GetMapping
    public List<ContentResponse> getAll(
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String title
    ) {
        return contentService.getAll(courseId, title);
    }

    @GetMapping("/{id}")
    public ContentResponse getById(@PathVariable Long id) {
        return contentService.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'INSTRUCTOR')")
    public ContentResponse update(
            @PathVariable Long id,
            @RequestBody @Valid ContentSaveRequest request
    ) {
        return contentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('OPERATOR', 'INSTRUCTOR')")
    public void delete(@PathVariable Long id) {
        contentService.delete(id);
    }
}
package com.umutgldn.patikaclone.path.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.umutgldn.patikaclone.path.dto.PathResponse;
import com.umutgldn.patikaclone.path.dto.PathSaveRequest;
import com.umutgldn.patikaclone.path.service.PathService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/paths")
@RequiredArgsConstructor
public class PathController {

    private final PathService pathService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PathResponse crete(@RequestBody @Valid PathSaveRequest request) {
        return pathService.create(request);
    }

    @GetMapping
    public List<PathResponse> getAll() {
        return pathService.getAll();
    }

    @GetMapping("/{id}")
    public PathResponse getById(@PathVariable Long id) {
        return pathService.getById(id);
    }

    @PutMapping("/{id}")
    public PathResponse update(@PathVariable Long id, @RequestBody @Valid PathSaveRequest request) {
        return pathService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        pathService.delete(id);
    }
}

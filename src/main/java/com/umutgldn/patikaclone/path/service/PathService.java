package com.umutgldn.patikaclone.path.service;

import java.util.List;

import com.umutgldn.patikaclone.path.dto.PathResponse;
import com.umutgldn.patikaclone.path.dto.PathSaveRequest;

public interface PathService {
    PathResponse create(PathSaveRequest request);

    List<PathResponse> getAll();

    PathResponse getById(Long id);

    PathResponse update(Long id, PathSaveRequest request);

    void delete(Long id);

}

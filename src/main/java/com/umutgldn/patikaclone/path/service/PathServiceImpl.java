package com.umutgldn.patikaclone.path.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.umutgldn.patikaclone.common.exception.BusinessException;
import com.umutgldn.patikaclone.path.dto.PathResponse;
import com.umutgldn.patikaclone.path.dto.PathSaveRequest;
import com.umutgldn.patikaclone.path.entity.Path;
import com.umutgldn.patikaclone.path.repository.PathRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PathServiceImpl implements PathService {

    private final PathRepository pathRepository;

    @Override
    public PathResponse create(PathSaveRequest request) {
        if (pathRepository.existsByNameIgnoreCase(request.name())) {
            throw new BusinessException("Bu patika zaten mevcut");
        }
        Path path = new Path();
        path.setName(request.name());
        Path savedPath = pathRepository.save(path);
        return toResponse(savedPath);
    }

    @Override
    public List<PathResponse> getAll() {
        return pathRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public PathResponse getById(Long id) {
        return toResponse(findPathById(id));
    }

    @Override
    public PathResponse update(Long id, PathSaveRequest request) {
        Path path = findPathById(id);
        if(!path.getName().equalsIgnoreCase(request.name()) && pathRepository.existsByNameIgnoreCase(request.name())){
            throw new BusinessException("Bu patika zaten mevcut");
        }
        path.setName(request.name());
        return toResponse(pathRepository.save(path));
    }

    @Override
    public void delete(Long id) {
        pathRepository.delete(findPathById(id));
    }

    private Path findPathById(Long id) {
        return pathRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Patika bulunamadı"));

    }

    private PathResponse toResponse(Path path) {
        return new PathResponse(path.getId(), path.getName());
    }

}

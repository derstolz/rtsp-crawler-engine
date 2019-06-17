package com.storage.cameras.service;

import com.storage.cameras.dao.CameraDao;
import com.storage.cameras.mapper.CameraToResourceMapper;
import com.storage.cameras.model.Camera;
import com.storage.cameras.rest.params.PostCameraParams;
import com.storage.cameras.rest.resource.CameraResource;
import com.storage.cameras.rest.params.SearchCameraParams;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(propagation = REQUIRED)
public class CameraServiceImpl implements CameraService {
    private final CameraDao cameraDao;
    private final CameraToResourceMapper mapper = CameraToResourceMapper.INSTANCE;

    @Override
    public CameraResource save(final PostCameraParams params) {
        final Camera camera = cameraDao.updateOrCreateCamera(params);
        return mapper.convert(camera);
    }

    @Override
    public CameraResource get(final Long id) throws NotFoundException {
        return cameraDao.get(id)
                .map(mapper::convert)
                .orElseThrow(() -> new NotFoundException(format("Camera with id %s was not found.", id)));
    }

    @Override
    public CameraResource get(final String rtspUrl) throws NotFoundException {
        return cameraDao.getByUrl(rtspUrl)
                .map(mapper::convert)
                .orElseThrow(() -> new NotFoundException(format("Camera with url %s was not found.", rtspUrl)));
    }

    @Override
    public List<CameraResource> search(final SearchCameraParams params) {
        return cameraDao.search(params)
                .stream()
                .map(mapper::convert)
                .collect(toList());
    }

    @Override
    public List<CameraResource> getAll() {
        return cameraDao.getAll()
                .stream()
                .map(mapper::convert)
                .collect(toList());
    }
}
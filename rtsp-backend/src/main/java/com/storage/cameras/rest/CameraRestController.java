package com.storage.cameras.rest;

import com.storage.cameras.rest.params.PostCameraParams;
import com.storage.cameras.rest.params.SearchCameraParams;
import com.storage.cameras.rest.resource.CameraIdentifiersResource;
import com.storage.cameras.rest.resource.CameraResource;
import com.storage.cameras.rest.resource.CameraResourceContainer;
import com.storage.cameras.rest.validator.PostCameraParamsValidator;
import com.storage.cameras.rest.validator.SearchCameraParamsValidator;
import com.storage.cameras.service.CameraService;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static com.storage.cameras.model.RequestPath.CAMERAS_URL;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(CAMERAS_URL)
@AllArgsConstructor
@Slf4j
public class CameraRestController {

    private final CameraService cameraService;

    @PutMapping(value = "/import", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity receive(@RequestBody final PostCameraParams params) {
        new PostCameraParamsValidator(params).validate();
        log.info("Import a camera: {}", params.getUrl());
        return ok(cameraService.save(params));
    }

    @SneakyThrows
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity get(@RequestParam(required = false) final Long id,
                              @RequestParam(required = false) final String rtspUrl) {
        if (id != null) {
            log.info("Get a camera: {}", id);
            return ok(cameraService.get(id));
        }
        if (isNotBlank(rtspUrl)) {
            log.info("Get a camera: {}", rtspUrl);
            return ok(cameraService.get(rtspUrl));
        }
        final List<CameraResource> cameras = cameraService.getAll();
        return ok(new CameraResourceContainer(cameras.size(), cameras));
    }

    @SneakyThrows
    @GetMapping(path = "/ids", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getCameraIds() {
        log.info("Getting camera ids");
        final List<Long> cameraIds = cameraService.getCameraIds();
        return ok(new CameraIdentifiersResource(cameraIds));
    }

    @SneakyThrows
    @PostMapping(value = "/search", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity search(@RequestBody @Valid SearchCameraParams params) {
        new SearchCameraParamsValidator(params).validate();
        final List<CameraResource> cameras = cameraService.search(params);
        return ok(new CameraResourceContainer(cameras.size(), cameras));
    }
}

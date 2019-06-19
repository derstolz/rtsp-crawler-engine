package com.storage.cameras.background;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.storage.cameras.dao.CameraDao;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DeleteUnconnectedBackgroundJob {

    private final CameraDao cameraDao;

    @Scheduled
    // adjust the annotation so this method will run each hour
    public void job() {
        // delete unconnected cameras
        // make a decision how to delete them - choose preferred period of time
    }
}

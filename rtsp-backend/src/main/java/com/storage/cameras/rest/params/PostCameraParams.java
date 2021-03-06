package com.storage.cameras.rest.params;

import com.storage.cameras.model.CameraStatus;
import java.util.List;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static com.storage.cameras.model.Camera.MAX_COMMENT_LENGTH;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCameraParams {

    private CameraStatus status;

    @Size(max = MAX_COMMENT_LENGTH)
    private String comment;

    private String url;

    private String city;

    private String countryCode;

    private String countryName;

    private String isp;

    private List<String> keywords;
    
    private String base64ImageData;
    
    private List<LabelParams> labels;
}

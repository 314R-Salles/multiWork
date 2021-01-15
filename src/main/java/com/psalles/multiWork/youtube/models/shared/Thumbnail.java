package com.psalles.multiWork.youtube.models.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Thumbnail {
    private Long width;
    private Long height;
    private String url;
}
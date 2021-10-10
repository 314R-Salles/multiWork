package com.psalles.multiWork.youtube.models.dtos;

import com.psalles.multiWork.youtube.models.shared.Thumbnail;
import lombok.Builder;
import lombok.Data;

import java.util.Map;


@Data
@Builder
public class PlaylistDto {

    private String title;
    private String id;
    private String status;
    private int itemCount;
    private Map<String, Thumbnail> thumbnails;

}

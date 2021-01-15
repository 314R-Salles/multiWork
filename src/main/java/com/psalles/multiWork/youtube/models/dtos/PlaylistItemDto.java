package com.psalles.multiWork.youtube.models.dtos;

import com.psalles.multiWork.youtube.models.shared.Thumbnail;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;


@Data
@Builder
public class PlaylistItemDto {

    private String title;
    private String description;
    private Long position;
    private LocalDateTime publishedAt;
    private String videoId;
    private Map<String, Thumbnail> thumbnails;

}

package com.psalles.multiWork.youtube.models.dtos;

import com.psalles.multiWork.youtube.models.shared.Thumbnail;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class LightPlaylistItemDto {

    private String title;
    private Long position;
    private String videoId;
    private Thumbnail thumbnail;

    public LightPlaylistItemDto(PlaylistItemDto base) {
        title = base.getTitle();
        position = base.getPosition();
        videoId = base.getVideoId();
        thumbnail = base.getThumbnails().get("default");
    }
}

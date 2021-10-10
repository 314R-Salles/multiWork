package com.psalles.multiWork.youtube.models.youtubeApiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.psalles.multiWork.youtube.models.shared.Thumbnail;
import lombok.Data;

import java.util.Map;

/**
 * A "light" version of Youtube PlaylistItem model, since
 * 1°) I can't deserialize most of their models.
 * 2°) I don't need all of the fields, and i'll store them in cache so i'll save some space.
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Playlist {
    private String id;
    private Snippet snippet;
    private Status status;
    private ContentDetails contentDetails;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Snippet {
        private String title;
        private Map<String, Thumbnail> thumbnails;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Status {
        private String privacyStatus;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ContentDetails {
        private int itemCount;
    }
}
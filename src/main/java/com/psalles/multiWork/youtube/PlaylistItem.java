package com.psalles.multiWork.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * A "light" version of Youtube PlaylistItem model, since
 * 1°) I can't deserialize most of their models.
 * 2°) I don't need all of the fields, and i'll store them in cache so i'll save some space.
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistItem {
    private Snippet snippet;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Snippet {
        private String title;
        private String description;
        private Long position;
        private LocalDateTime publishedAt;
        private Resource resourceId;
        private Map<String, Thumbnail> thumbnails;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Thumbnail {
        private Long width;
        private Long height;
        private String url;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Resource {
        private String videoId;
    }
}
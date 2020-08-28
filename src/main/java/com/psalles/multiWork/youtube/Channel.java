package com.psalles.multiWork.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.services.youtube.model.ImageSettings;
import com.google.api.services.youtube.model.PropertyValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 * A "light" version of Youtube Channel model, since
 * 1°) I can't deserialize most of their models.
 * 2°) I don't need all of the fields, and i'll store them in cache so i'll save some space.
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Channel {
    private Snippet snippet;
    private ContentDetails contentDetails;
    private Statistics statistics;
    private BrandingSettings brandingSettings;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Snippet {
        private String title;
        private String description;
        private String customUrl;
        private LocalDateTime publishedAt;
        private Map<String, Thumbnail> thumbnails;
        private String defaultLanguage;
        private String country;
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
    public static class ContentDetails {
        private RelatedPlaylists relatedPlaylists;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RelatedPlaylists {
        private String uploads;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Statistics {
        private long viewCount;
        private long subscriberCount;
        private long videoCount;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BrandingSettings {
        private ChannelSettings channel;
        private List<PropertyValue> hints;
        private ImageSettings image;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChannelSettings {
        private String defaultTab;
        private String description;
        private String featuredChannelsTitle;
        private List<String> featuredChannelsUrls;
        private String keywords;
        private String profileColor;
        private String title;
        private String trackingAnalyticsAccountId;
        private String unsubscribedTrailer;
    }



}

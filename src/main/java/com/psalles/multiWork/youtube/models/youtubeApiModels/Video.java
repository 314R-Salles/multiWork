package com.psalles.multiWork.youtube.models.youtubeApiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.client.util.DateTime;
import com.psalles.multiWork.youtube.models.shared.Thumbnail;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Video {

    private VideoContentDetails contentDetails;
    private String id;
    //        private Map<String, VideoLocalization> localizations; // peut etre plus tard
//        private VideoPlayer player;
    private VideoSnippet snippet;
    private VideoStatistics statistics;
//        private VideoStatus status;
//        private VideoTopicDetails topicDetails;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VideoContentDetails {
        private String caption;
        private String definition;
        private String dimension;
        private String duration;
        private Boolean hasCustomThumbnail;
        private Boolean licensedContent;
        private String projection;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VideoSnippet {
        private String categoryId;
        private String channelId;
        private String channelTitle;
        private String defaultAudioLanguage;
        private String defaultLanguage;
        private String description;
        private String liveBroadcastContent;
        private DateTime publishedAt;
        private List<String> tags;
        private Map<String, Thumbnail> thumbnails;
        private String title;

    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VideoStatistics {
        private BigInteger commentCount;
        private BigInteger dislikeCount;
        private BigInteger favoriteCount;
        private BigInteger likeCount;
        private BigInteger viewCount;
    }


}


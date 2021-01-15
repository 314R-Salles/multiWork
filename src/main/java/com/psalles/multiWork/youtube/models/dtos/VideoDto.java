package com.psalles.multiWork.youtube.models.dtos;

import com.google.api.client.util.DateTime;
import com.psalles.multiWork.youtube.models.shared.Thumbnail;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class VideoDto {
    private String id;
    private String caption;
    private String definition;
    private String dimension;
    private String duration;
    private Boolean hasCustomThumbnail;
    private Boolean licensedContent;
    private String projection;

    private String categoryId;
    private String channelId;
    private String channelTitle;
    private String defaultAudioLanguage;
    private String defaultLanguage;
    private String description;
    private String liveBroadcastContent;
    private DateTime publishedAt;
    private List<String> tags;
    private String title;

    private BigInteger commentCount;
    private BigInteger dislikeCount;
    private BigInteger favoriteCount;
    private BigInteger likeCount;
    private BigInteger viewCount;

    private Map<String, Thumbnail> thumbnails;
}
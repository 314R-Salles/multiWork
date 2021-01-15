package com.psalles.multiWork.youtube.models.dtos;

import com.google.api.services.youtube.model.ImageSettings;
import com.google.api.services.youtube.model.PropertyValue;
import com.psalles.multiWork.youtube.models.shared.Thumbnail;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Data
@Builder
public class ChannelDto {
    private String title;
    private String description;
    private String customUrl;
    private LocalDateTime publishedAt;
    private Map<String, Thumbnail> thumbnails;
    private String defaultLanguage;
    private String country;

    private String uploads;

    private long viewCount;
    private long subscriberCount;
    private long videoCount;

    private List<PropertyValue> hints;
    private ImageSettings image;

    private String defaultTab;
    private String featuredChannelsTitle;
    private List<String> featuredChannelsUrls;
    private String keywords;
    private String profileColor;
    private String trackingAnalyticsAccountId;
    private String unsubscribedTrailer;
}



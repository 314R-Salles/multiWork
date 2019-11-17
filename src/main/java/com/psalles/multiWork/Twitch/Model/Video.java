package com.psalles.multiWork.Twitch.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Video {

    @JsonAlias(value = "id")
    private String videoId;

    @JsonAlias(value = "user_id")
    private String userId;

    @JsonAlias(value = "user_name")
    private String userName;

    private String title;

    private String description;

    @JsonAlias(value = "created_at")
    private String createdAt;

    @JsonAlias(value = "published_at")
    private String publishedAt;

    private String url;

    @JsonAlias(value = "thumbnail_url")
    private String thumbnailUrl;

    private String viewable;

    @JsonAlias(value = "view_count")
    private String viewCount;

    private String language;

    private String type;

    private String duration;

}

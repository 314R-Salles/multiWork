package com.psalles.multiWork.Twitch.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class LiveData {


    @JsonAlias(value = "id")
    private String liveId;

    @JsonAlias(value = "user_id")
    private String userId;

    @JsonAlias(value = "user_name")
    private String username;

    @JsonAlias(value = "game_id")
    private String gameId;

    @JsonAlias(value = "type")
    private String type;

    @JsonAlias(value = "title")
    private String title;

    @JsonAlias(value = "viewer_count")
    private String viewerCount;

    @JsonAlias(value = "started_at")
    private String startTime;

    @JsonAlias(value = "language")
    private String language;

    @JsonAlias(value = "thumbnail_url")
    private String thumbnailUrl;

}

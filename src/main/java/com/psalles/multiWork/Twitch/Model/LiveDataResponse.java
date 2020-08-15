package com.psalles.multiWork.Twitch.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveDataResponse {

    List<LiveData> data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LiveData {

        @JsonAlias(value = "id")
        protected String liveId;

        @JsonAlias(value = "user_id")
        protected String userId;

        @JsonAlias(value = "user_name")
        protected String username;

        @JsonAlias(value = "game_id")
        protected String gameId;

        @JsonAlias(value = "type")
        protected String type;

        @JsonAlias(value = "title")
        protected String title;

        @JsonAlias(value = "viewer_count")
        protected String viewerCount;

        @JsonAlias(value = "started_at")
        protected String startTime;

        @JsonAlias(value = "language")
        protected String language;

        @JsonAlias(value = "thumbnail_url")
        protected String thumbnailUrl;
    }

}

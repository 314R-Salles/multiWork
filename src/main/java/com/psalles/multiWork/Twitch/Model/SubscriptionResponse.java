package com.psalles.multiWork.Twitch.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionResponse {

    List<Subscription> data;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Subscription {

        @JsonAlias(value = "from_id")
        private String fromId;

        @JsonAlias(value = "from_name")
        private String fromName;

        @JsonAlias(value = "to_id")
        private String toId;

        @JsonAlias(value = "to_name")
        private String toName;

        @JsonAlias(value = "followed_at")
        private String followedAt;
    }

}

package com.psalles.multiWork.Twitch.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class User {

    @JsonAlias(value = "id")
    private String userId;

    @JsonAlias(value = "login")
    private String login;

    @JsonAlias(value = "display_name")
    private String displayName;

    @JsonAlias(value = "type")
    private String type;

    @JsonAlias(value = "broadcaster_type")
    private String broadcasterType;

    @JsonAlias(value = "description")
    private String description;

    @JsonAlias(value = "profile_image_url")
    private String profileImageUrl;

    @JsonAlias(value = "offline_image_url")
    private String offlineImageUrl;

    @JsonAlias(value = "view_count")
    private BigDecimal viewCount;

    @JsonAlias(value = "email")
    private String email;
}

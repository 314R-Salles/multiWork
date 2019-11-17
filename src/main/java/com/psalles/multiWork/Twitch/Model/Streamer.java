package com.psalles.multiWork.Twitch.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Streamer {

    @JsonAlias(value = "id")
    public String userId;

    @JsonAlias(value = "login")
    public String username;

    @JsonAlias(value = "display_name")
    public String displayName;

    public String description;

    @JsonAlias(value = "profile_image_url")
    public String profileImage;

    @JsonAlias(value = "offline_image_url")
    public String offlineImage;

    @JsonAlias(value = "view_count")
    public BigDecimal viewCount;

}

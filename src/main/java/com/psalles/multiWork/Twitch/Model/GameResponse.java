package com.psalles.multiWork.Twitch.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameResponse {
    List<Game> data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class Game {

        String id;

        String name;

        @JsonAlias(value = "box_art_url")
        String boxArtUrl;

    }
}

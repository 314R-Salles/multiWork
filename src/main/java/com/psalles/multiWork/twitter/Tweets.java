package com.psalles.multiWork.twitter;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweets {
    private List<Tweet> data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tweet {

        @JsonAlias(value = "id")
        public String id;

        @JsonAlias(value = "text")
        public String text;

        public String url;

        public String level;

    }

    private String lastLevel;

}

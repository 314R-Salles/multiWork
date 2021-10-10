package com.psalles.multiWork.twitter;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterUser {
    private UserData data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserData {

        @JsonAlias(value = "id")
        public String userId;

        @JsonAlias(value = "name")
        public String name;

        @JsonAlias(value = "username")
        public String handle;

    }

}

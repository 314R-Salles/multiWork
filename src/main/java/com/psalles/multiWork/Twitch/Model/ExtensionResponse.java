package com.psalles.multiWork.Twitch.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtensionResponse {
    ExtensionMaps data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExtensionMaps {
        Map<String, Extension> panel;
        Map<String, Extension> component;
        Map<String, Extension> overlay;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Extension {
        boolean active;
        String id;
        String version;
        String name;
    }


}

package com.psalles.multiWork.Twitch.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class ExtensionResponse {
    ExtensionMaps data;
}

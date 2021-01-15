package com.psalles.multiWork.youtube.models.youtubeApiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ChannelResponse {
    private List<Channel> items;
}

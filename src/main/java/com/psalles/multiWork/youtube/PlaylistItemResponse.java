package com.psalles.multiWork.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PlaylistItemResponse {
    private List<PlaylistItem> items;
}

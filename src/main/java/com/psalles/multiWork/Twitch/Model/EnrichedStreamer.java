package com.psalles.multiWork.Twitch.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrichedStreamer extends Streamer {
    private EnrichedLiveData live;


    public EnrichedStreamer(Streamer base, EnrichedLiveData live) {
        this.userId = base.userId;
        this.username = base.username;
        this.displayName = base.displayName;
        this.description = base.description;
        this.profileImage = base.profileImage;
        this.offlineImage = base.offlineImage;
        this.viewCount = base.viewCount;
        this.live = live;
    }
}

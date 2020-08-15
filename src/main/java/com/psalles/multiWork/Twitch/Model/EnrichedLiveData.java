package com.psalles.multiWork.Twitch.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrichedLiveData extends LiveDataResponse.LiveData {

    private String gameName;
    private String gameIconUrl;

    public EnrichedLiveData(LiveDataResponse.LiveData base, GameResponse.Game game) {
        liveId = base.liveId;
        userId = base.userId;
        username = base.username;
        gameId = base.gameId;
        type = base.type;
        title = base.title;
        viewerCount = base.viewerCount;
        startTime = base.startTime;
        language = base.language;
        thumbnailUrl = base.thumbnailUrl;

        gameName = game.name;
        gameIconUrl = game.boxArtUrl;
    }

}

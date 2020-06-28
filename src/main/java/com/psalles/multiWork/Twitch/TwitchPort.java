package com.psalles.multiWork.Twitch;

import com.psalles.multiWork.Twitch.Model.EnrichedStreamer;
import com.psalles.multiWork.Twitch.Model.Extension;
import com.psalles.multiWork.Twitch.Model.User;
import com.psalles.multiWork.Twitch.Model.Video;

import java.util.List;
import java.util.Map;

public interface TwitchPort {

    User getUserInfoFromAuthToken(String token);

    List<Video> getStreamerVideos(String token, String streamerId);

    List<EnrichedStreamer> getAllStreamingInformation(String token, String userId);

    Map<String, List<Extension>> getUsersPanelExtensions(String token, String userId);

    void logout(String token);

}

package com.psalles.multiWork.Twitch;

import com.psalles.multiWork.Twitch.Model.EnrichedStreamer;
import com.psalles.multiWork.Twitch.Model.ExtensionResponse;
import com.psalles.multiWork.Twitch.Model.UserResponse;
import com.psalles.multiWork.Twitch.Model.VideoResponse;

import java.util.List;
import java.util.Map;

public interface TwitchPort {

    UserResponse.User getUserInfoFromAuthToken(String token);

    List<VideoResponse.Video> getStreamerVideos(String token, String streamerId);

    List<EnrichedStreamer> getAllStreamingInformation(String token, String userId);

    Map<String, List<ExtensionResponse.Extension>> getUsersPanelExtensions(String token, String userId);

    void logout(String token);

}

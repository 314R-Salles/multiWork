package com.psalles.multiWork.Twitch.Service;

import com.psalles.multiWork.Commons.Utils.CacheService;
import com.psalles.multiWork.Twitch.Model.EnrichedStreamer;
import com.psalles.multiWork.Twitch.Model.ExtensionResponse;
import com.psalles.multiWork.Twitch.Model.UserResponse;
import com.psalles.multiWork.Twitch.Model.VideoResponse;
import com.psalles.multiWork.Twitch.TwitchAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TwitchService {

    private final TwitchAdapter twitchAdapter;
    private final CacheService cacheService;

    @Autowired
    public TwitchService(TwitchAdapter twitchAdapter,
                         CacheService cacheService) {
        this.twitchAdapter = twitchAdapter;
        this.cacheService = cacheService;
    }

    @Cacheable("user")
    public UserResponse.User getUserInfoFromAuthToken(String token) {
        return this.twitchAdapter.getUserInfoFromAuthToken(token);
    }

    public List<VideoResponse.Video> getStreamerVideos(String token, String streamerId) {
        return this.twitchAdapter.getStreamerVideos(token, streamerId);
    }

    public List<EnrichedStreamer> getAllStreamingInformation(String token) {
        return this.twitchAdapter.getAllStreamingInformation(token, getUserInfoFromAuthToken(token).getUserId());
    }

    @Cacheable("extensions")
    public Map<String, List<ExtensionResponse.Extension>> getUsersPanelExtensions(String token) {
        return this.twitchAdapter.getUsersPanelExtensions(token, getUserInfoFromAuthToken(token).getUserId());
    }

    public void logout(String token) {
        clearCaches(token);
        this.twitchAdapter.logout(token);
    }

    private void clearCaches(String token) {
        cacheService.evictUser(token);
        cacheService.evictExtensions(token);
    }

}

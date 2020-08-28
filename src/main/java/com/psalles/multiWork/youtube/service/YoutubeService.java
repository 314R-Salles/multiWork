package com.psalles.multiWork.youtube.service;

import com.psalles.multiWork.Commons.Client.BaseHttpClient;
import com.psalles.multiWork.Commons.Utils.ParameterStringBuilder;
import com.psalles.multiWork.youtube.ChannelResponse;
import com.psalles.multiWork.youtube.PlaylistItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.util.Collections.singletonList;

@Service
public class YoutubeService {

    private final BaseHttpClient httpClient;

    String key = "AIzaSyCLK9gXBHWudJOq2cRY96NAn29LHzvX95w";
    String youtubeV3 = "https://www.googleapis.com/youtube/v3";

    String usefulPartsString = "brandingSettings,contentDetails,snippet,statistics";
    List<String> usefulParts = Arrays.asList(usefulPartsString.split(","));

    String usefulVideoPartsString = "snippet,contentDetails";
    List<String> usefulVideoParts = Arrays.asList(usefulVideoPartsString.split(","));

    @Autowired
    public YoutubeService(BaseHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Cacheable("channels")
    public ChannelResponse getChannel(String channelId) {
        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("part", usefulParts);
        parameters.put("id", singletonList(channelId));
        parameters.put("key", singletonList(key));

        String url = youtubeV3 + "/channels?" + ParameterStringBuilder.getParamsStringListCommaSeparated(parameters);
        return httpClient.makeCall(HttpMethod.GET, url, ChannelResponse.class, null, null);
    }

    @Cacheable("linkedChannels")
    public ChannelResponse getLinkedChannels(String channelId) {
        ChannelResponse channel = getChannel(channelId);
        List<String> channelIds = channel.getItems().get(0).getBrandingSettings().getChannel().getFeaturedChannelsUrls();

        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("part", usefulParts);
        parameters.put("id", channelIds);
        parameters.put("key", singletonList(key));

        String url = youtubeV3 + "/channels?" + ParameterStringBuilder.getParamsStringListCommaSeparated(parameters);

        return httpClient.makeCall(HttpMethod.GET, url, ChannelResponse.class, null, null);
    }

    @Cacheable("videos")
    public PlaylistItemResponse getRecentVideos(String channelId) {
        ChannelResponse channel = getChannel(channelId);
        String uploadsId = channel.getItems().get(0).getContentDetails().getRelatedPlaylists().getUploads();

        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("part", usefulVideoParts);
        parameters.put("maxResults", singletonList("50"));
        parameters.put("playlistId", singletonList(uploadsId));
        parameters.put("key", singletonList(key));

        String videosUrl = youtubeV3 + "/playlistItems?" + ParameterStringBuilder.getParamsStringListCommaSeparated(parameters);

        return httpClient.makeCall(HttpMethod.GET, videosUrl, PlaylistItemResponse.class, null, null);
    }
}



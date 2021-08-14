package com.psalles.multiWork.youtube.service;

import com.psalles.multiWork.Commons.Client.BaseHttpClient;
import com.psalles.multiWork.Commons.Utils.ParameterStringBuilder;
import com.psalles.multiWork.youtube.mappers.ChannelMapper;
import com.psalles.multiWork.youtube.mappers.PlaylistItemMapper;
import com.psalles.multiWork.youtube.mappers.VideoMapper;
import com.psalles.multiWork.youtube.models.dtos.ChannelDto;
import com.psalles.multiWork.youtube.models.dtos.PlaylistItemDto;
import com.psalles.multiWork.youtube.models.dtos.VideoDto;
import com.psalles.multiWork.youtube.models.youtubeApiModels.ChannelResponse;
import com.psalles.multiWork.youtube.models.youtubeApiModels.PlaylistItemResponse;
import com.psalles.multiWork.youtube.models.youtubeApiModels.VideoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Service
public class YoutubeCacheService {

    /**
     * J'ai le droit à 10k appels par jour.
     * <p>
     * Je ne veux pas tout mettre en cache.
     * <p>
     * Les personnes qui ont besoin de données pour peupler leur site internet ok.
     */

    private final BaseHttpClient httpClient;
    //    String key = "AIzaSyCM9nlF4Oa3ssHcZiuVZErxKJRzqASZ4Ac";
    String key = "AIzaSyBki9jrSNFeLb1IWkx4sM9w2BrBjJQwhfE";
    String youtubeV3 = "https://www.googleapis.com/youtube/v3";
    String usefulPartsString = "brandingSettings,contentDetails,snippet,statistics";
    List<String> usefulParts = Arrays.asList(usefulPartsString.split(","));
    String usefulPlaylistPartsString = "snippet,contentDetails";
    List<String> usefulPlaylistParts = Arrays.asList(usefulPlaylistPartsString.split(","));
    String usefulVideoPartsString = "snippet,contentDetails,statistics";
    List<String> usefulVideoParts = Arrays.asList(usefulVideoPartsString.split(","));

    @Autowired
    public YoutubeCacheService(BaseHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Cacheable("channels")
    public ChannelDto getChannel(String channelId) {
        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("part", usefulParts);
        parameters.put("id", singletonList(channelId));
        parameters.put("key", singletonList(key));

        String url = youtubeV3 + "/channels?" + ParameterStringBuilder.getParamsStringListCommaSeparated(parameters);
        ChannelResponse response = httpClient.makeCall(HttpMethod.GET, url, ChannelResponse.class, null, null);
        return ChannelMapper.toDtos(response).get(0);
    }

    @Cacheable(value = "linkedChannels", key = "{ #channelId}")
    public List<ChannelDto> getLinkedChannels(ChannelDto channel, String channelId) {
        List<String> channelIds = channel.getFeaturedChannelsUrls();

        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("part", usefulParts);
        parameters.put("id", channelIds);
        parameters.put("key", singletonList(key));

        String url = youtubeV3 + "/channels?" + ParameterStringBuilder.getParamsStringListCommaSeparated(parameters);

        ChannelResponse response = httpClient.makeCall(HttpMethod.GET, url, ChannelResponse.class, null, null);

        return ChannelMapper.toDtos(response);
    }

    @Cacheable(value = "uploadPlaylist", key = "{ #channelId}")
    public List<PlaylistItemDto> getAllUploadedElements(ChannelDto channel, String channelId) {
        String uploadsId = channel.getUploads();

        List<PlaylistItemResponse> lists = new ArrayList<>();

        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("part", usefulPlaylistParts);
        parameters.put("maxResults", singletonList("50"));
        parameters.put("playlistId", singletonList(uploadsId));
        parameters.put("key", singletonList(key));

        String videosUrl = youtubeV3 + "/playlistItems?" + ParameterStringBuilder.getParamsStringListCommaSeparated(parameters);

        PlaylistItemResponse response = httpClient.makeCall(HttpMethod.GET, videosUrl, PlaylistItemResponse.class, null, null);
        lists.add(response);

        // if more than 50 results, need to retrieve next pages.
        while (response.getNextPageToken() != null) {
            parameters.put("pageToken", singletonList(response.getNextPageToken()));
            videosUrl = youtubeV3 + "/playlistItems?" + ParameterStringBuilder.getParamsStringListCommaSeparated(parameters);

            response = httpClient.makeCall(HttpMethod.GET, videosUrl, PlaylistItemResponse.class, null, null);
            lists.add(response);
        }
        return PlaylistItemMapper.toDtos(lists);
    }


    @Cacheable(value = "videos", key = "{ #channelId}")
    public List<VideoDto> getVideos(List<PlaylistItemDto> playlist, String channelId) {

        // utilisation du reduce sans identité pour pas commencer par un ""+","+"id"
        List<String> ids = playlist.stream()
                .map(PlaylistItemDto::getVideoId).collect(toList());

        int size = ids.size();
        int numberOfLists = size / 50 + 1;

        List<VideoResponse> lists = new ArrayList<>();

        for (int i = 0; i < numberOfLists; i++) {

            HashMap<String, List<String>> parameters = new HashMap<>();
            parameters.put("part", usefulVideoParts);
            parameters.put("id", singletonList(new ArrayList<>(ids.subList(i * 50, Math.min((i + 1) * 50, size))).stream().reduce((a, b) -> a + "," + b).get()));
            parameters.put("key", singletonList(key));

            String videosUrl = youtubeV3 + "/videos?" + ParameterStringBuilder.getParamsStringListCommaSeparated(parameters);
            VideoResponse response = httpClient.makeCall(HttpMethod.GET, videosUrl, VideoResponse.class, null, null);
            lists.add(response);
        }
        return VideoMapper.toDtos(lists);
    }

}

package com.psalles.multiWork.youtube.service;

import com.psalles.multiWork.youtube.models.dtos.ChannelDto;
import com.psalles.multiWork.youtube.models.dtos.PlaylistItemDto;
import com.psalles.multiWork.youtube.models.dtos.VideoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YoutubeService {

    private final YoutubeCacheService youtubeCacheService;

    @Autowired
    public YoutubeService(YoutubeCacheService youtubeCacheService) {
        this.youtubeCacheService = youtubeCacheService;
    }

    public ChannelDto getChannel(String channelId) {
        return youtubeCacheService.getChannel(channelId);
    }

    public List<ChannelDto> getLinkedChannels(String channelId) {
        ChannelDto channel = getChannel(channelId);
        return youtubeCacheService.getLinkedChannels(channel, channelId);
    }

    public List<PlaylistItemDto> getAllUploadedElements(String channelId) {
        ChannelDto channel = getChannel(channelId);
        return youtubeCacheService.getAllUploadedElements(channel, channelId);
    }

    public List<VideoDto> getVideos(String channelId) {
        List<PlaylistItemDto> allUploadedElements = getAllUploadedElements(channelId);
        return youtubeCacheService.getVideos(allUploadedElements, channelId);
    }
}

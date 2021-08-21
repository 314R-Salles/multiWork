package com.psalles.multiWork.youtube.service;

import com.psalles.multiWork.Commons.exceptions.ResourceNotFoundException;
import com.psalles.multiWork.Commons.exceptions.TechnicalException;
import com.psalles.multiWork.youtube.models.dtos.ChannelDto;
import com.psalles.multiWork.youtube.models.dtos.LightPlaylistItemDto;
import com.psalles.multiWork.youtube.models.dtos.PlaylistItemDto;
import com.psalles.multiWork.youtube.models.dtos.VideoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        String uploadsId = channel.getUploads();
        return youtubeCacheService.getPlaylist(uploadsId);
    }

    public List<PlaylistItemDto> getPlaylist(String playlistId) {
        return youtubeCacheService.getPlaylist(playlistId);
    }

    public List<LightPlaylistItemDto> getLightPlaylist(String playlistId) {
        try {
            return youtubeCacheService.getPlaylist(playlistId).stream()
                    .filter(video -> !"Deleted video" .equals(video.getTitle()))
                    .filter(video -> !"Private video" .equals(video.getTitle()))
                    .map(LightPlaylistItemDto::new)
                    .collect(Collectors.toList());
        } catch (ResourceNotFoundException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            throw new TechnicalException("Something went wrong somewhere i guess");
        }
    }

    public List<VideoDto> getVideos(String channelId) {
        List<PlaylistItemDto> allUploadedElements = getAllUploadedElements(channelId);
        return youtubeCacheService.getVideos(allUploadedElements, channelId);
    }
}

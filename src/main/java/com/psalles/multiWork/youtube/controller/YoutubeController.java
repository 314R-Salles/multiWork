package com.psalles.multiWork.youtube.controller;

import com.psalles.multiWork.youtube.models.dtos.ChannelDto;
import com.psalles.multiWork.youtube.models.dtos.PlaylistItemDto;
import com.psalles.multiWork.youtube.models.dtos.VideoDto;
import com.psalles.multiWork.youtube.service.YoutubeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@RequestMapping("/youtube/user")
public class YoutubeController {

    private final YoutubeService youtubeService;

    @Autowired
    public YoutubeController(YoutubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    @ApiOperation("get youtube")
    @GetMapping("/{channelId}")
    public ChannelDto getStreamingStatus(@PathVariable String channelId) {
        return youtubeService.getChannel(channelId);
    }

    @ApiOperation("get youtube")
    @GetMapping("/{channelId}/linkedChannels")
    public List<ChannelDto> getLinkedChannels(@PathVariable String channelId) {
        return youtubeService.getLinkedChannels(channelId);
    }


    @ApiOperation("get youtube")
    @GetMapping("/{channelId}/allUploads")
    public List<PlaylistItemDto> getLastUploads(@PathVariable String channelId) {
        return youtubeService.getAllUploadedElements(channelId);
    }

    @ApiOperation("get youtube")
    @GetMapping("/{channelId}/allVideos")
    public List<VideoDto> getLastVideos(@PathVariable String channelId) {
        return youtubeService.getVideos(channelId);
    }
}

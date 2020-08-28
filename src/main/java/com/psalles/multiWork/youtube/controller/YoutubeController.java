package com.psalles.multiWork.youtube.controller;

import com.psalles.multiWork.youtube.Channel;
import com.psalles.multiWork.youtube.PlaylistItem;
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
    public Channel getStreamingStatus(@PathVariable String channelId) {
        return youtubeService.getChannel(channelId).getItems().get(0);
    }

    @ApiOperation("get youtube")
    @GetMapping("/{channelId}/linkedChannels")
    public List<Channel> getLinkedChannels(@PathVariable String channelId) {
        return youtubeService.getLinkedChannels(channelId).getItems();
    }


    @ApiOperation("get youtube")
    @GetMapping("/{channelId}/videos")
    public List<PlaylistItem> getLastUploads(@PathVariable String channelId) {
        return youtubeService.getRecentVideos(channelId).getItems();
    }
}

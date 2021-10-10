package com.psalles.multiWork.youtube.controller;

import com.psalles.multiWork.youtube.models.dtos.*;
import com.psalles.multiWork.youtube.service.YoutubeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = " ", tags = "Youtube endpoints")
@RestController
@RequestMapping("/youtube")
public class YoutubeController {

    private final YoutubeService youtubeService;

    @Autowired
    public YoutubeController(YoutubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    @ApiOperation(value = "Get channel basic properties")
    @GetMapping("/channel/{channelId}")
    public ChannelDto getStreamingStatus(@PathVariable String channelId) {
        return youtubeService.getChannel(channelId);
    }

//    @ApiOperation(value = "Get list of featured channels")
//    @GetMapping("/{channelId}/featured")
//    public List<ChannelDto> getFeaturedChannels(@PathVariable String channelId) {
//        return youtubeService.getLinkedChannels(channelId);
//    }

    @ApiOperation(value = "Get all videos of a channel (few properties)")
    @GetMapping("/channel/{channelId}/allUploads")
    public List<PlaylistItemDto> getLastUploads(@PathVariable String channelId) {
        return youtubeService.getAllUploadedElements(channelId);
    }

    @ApiOperation(value = "Get all videos of a channel (all properties)")
    @GetMapping("/channel/{channelId}/allVideos")
    public List<VideoDto> getLastVideos(@PathVariable String channelId) {
        return youtubeService.getVideos(channelId);
    }

    @ApiOperation(value = "Get all videos of a playlist (few properties)")
    @GetMapping("/playlist/{playlistId}")
    public List<LightPlaylistItemDto> getPlaylist(@PathVariable String playlistId) {
        return youtubeService.getLightPlaylist(playlistId);
    }

    @ApiOperation(value = "Get all playlists of an user (OAuth token)")
    @GetMapping("/playlists")
    public List<PlaylistDto> getPlaylists(
            @ApiParam(type = "String", value = "Token from youtube login", required = true)
            @RequestHeader("youtube") String token) {
        return youtubeService.getPlaylists(token);
    }

}

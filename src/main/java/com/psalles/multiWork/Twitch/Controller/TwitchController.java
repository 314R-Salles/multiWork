package com.psalles.multiWork.Twitch.Controller;

import com.psalles.multiWork.Twitch.Model.EnrichedStreamer;
import com.psalles.multiWork.Twitch.Model.Extension;
import com.psalles.multiWork.Twitch.Model.User;
import com.psalles.multiWork.Twitch.Model.Video;
import com.psalles.multiWork.Twitch.Service.TwitchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Api(description = "Calls Twitch API for basic data on streams")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/twitch")
public class TwitchController {

    private final TwitchService twitchService;

    @Autowired
    public TwitchController(TwitchService twitchService) {
        this.twitchService = twitchService;
    }

    // TODO FIX THIS
    @ApiOperation("Save my token")
    @PostMapping("/login")
    public void login(@RequestBody String token) {
        twitchService.saveToken(token);
    }

    @ApiOperation("Get my data")
    @GetMapping("/getMyUser")
    public User getMyData() {
        return twitchService.getUserInfoFromAuthToken();
    }

    @ApiOperation("Get user videos")
    @GetMapping("/user/{userId}/videos")
    public List<Video> getVideos(@PathVariable String userId) throws IOException {
        return twitchService.getUserVideos(userId);
    }

    @ApiOperation("Get streaming status of {userId}'s subscription list")
    @GetMapping("/streams/user/{userId}")
    public List<EnrichedStreamer> getStreamingStatus(@PathVariable String userId) throws IOException {
        return twitchService.getStreamingStatus(userId);
    }

    @ApiOperation("Get users panel extensions")
    @GetMapping("/extensions/user/{userId}")
    @Cacheable("extensions")
    public Map<String, List<Extension>> getUsersPanelExtensions(@PathVariable String userId) throws IOException {
        return twitchService.getUsersPanelExtensions(userId);
    }
}

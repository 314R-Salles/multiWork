package com.psalles.multiWork.Twitch.Controller;

import com.psalles.multiWork.Twitch.Model.EnrichedStreamer;
import com.psalles.multiWork.Twitch.Model.Extension;
import com.psalles.multiWork.Twitch.Model.User;
import com.psalles.multiWork.Twitch.Model.Video;
import com.psalles.multiWork.Twitch.Service.TwitchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api
@RestController
@RequestMapping("/twitch")
public class TwitchController {

    private final TwitchService twitchService;

    @Autowired
    public TwitchController(TwitchService twitchService) {
        this.twitchService = twitchService;
    }

    @ApiOperation("Get my data")
    @GetMapping("/getMyUser")
    public User getMyData(@RequestHeader("twitch") String token) {
        return twitchService.getUserInfoFromAuthToken(token);
    }

    @ApiOperation("Get user videos")
    @GetMapping("/user/{streamerId}/videos")
    public List<Video> getVideos(@RequestHeader("twitch") String token, @PathVariable String streamerId) {
        return twitchService.getStreamerVideos(token, streamerId);
    }

    @ApiOperation("Get streaming status of {userId}'s subscription list")
    @GetMapping("/streams")
    public List<EnrichedStreamer> getStreamingStatus(@RequestHeader("twitch") String token) {
        return twitchService.getAllStreamingInformation(token);
    }

    @ApiOperation("Logout: invalidate token and clear cache")
    @PostMapping("/logout")
    public void logout(@RequestHeader("twitch") String token) {
        twitchService.logout(token);
    }

    @ApiOperation("Get users panel extensions")
    @GetMapping("/extensions")
    public Map<String, List<Extension>> getUsersPanelExtensions(@RequestHeader("twitch") String token) {
        return twitchService.getUsersPanelExtensions(token);
    }
}

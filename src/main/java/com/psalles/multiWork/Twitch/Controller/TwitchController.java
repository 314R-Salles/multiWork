package com.psalles.multiWork.Twitch.Controller;

import com.psalles.multiWork.Twitch.Model.EnrichedStreamer;
import com.psalles.multiWork.Twitch.Model.ExtensionResponse;
import com.psalles.multiWork.Twitch.Model.UserResponse;
import com.psalles.multiWork.Twitch.Model.VideoResponse;
import com.psalles.multiWork.Twitch.Service.TwitchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.DoubleBinaryOperator;

@Api(description = " ", tags = "Twitch endpoints")
@RestController
@RequestMapping("/twitch")
public class TwitchController {

    private final TwitchService twitchService;

    @Autowired
    public TwitchController(TwitchService twitchService) {
        this.twitchService = twitchService;
    }

    @ApiOperation("Get logged user properties")
    @GetMapping("/getMyUser")
    public UserResponse.User getMyData(
            @ApiParam(type = "String", value = "Token from twitch login, authenticate all requests", required = true)
            @RequestHeader("twitch") String token) {
        return twitchService.getUserInfoFromAuthToken(token);
    }

    @ApiOperation("Get list of available recorded streams for the specified user")
    @GetMapping("/user/{userId}/videos")
    public List<VideoResponse.Video> getVideos(
            @ApiParam(type = "String", value = "Token from twitch login, authenticate all requests", required = true)
            @RequestHeader("twitch") String token,
            @ApiParam(type = "String", value = "The twitch id of the user", required = true)
            @PathVariable String userId) {
        return twitchService.getStreamerVideos(token, userId);
    }

    @ApiOperation("Get streaming status of people you follow")
    @GetMapping("/streams")
    public List<EnrichedStreamer> getStreamingStatus(
            @ApiParam(type = "String", value = "Token from twitch login, authenticate all requests", required = true)
            @RequestHeader("twitch") String token) {
        return twitchService.getAllStreamingInformation(token);
    }

    @ApiOperation("Logout: invalidate token and clear cache")
    @PostMapping("/logout")
    public void logout(
            @ApiParam(type = "String", value = "Token from twitch login, authenticate all requests", required = true)
            @RequestHeader("twitch") String token) {
        twitchService.logout(token);
    }

    @ApiOperation("Get users panel extensions of people you follow (iframe urls)")
    @GetMapping("/extensions")
    public Map<String, List<ExtensionResponse.Extension>> getUsersPanelExtensions(
            @ApiParam(type = "String", value = "Token from twitch login, authenticate all requests", required = true)
            @RequestHeader("twitch") String token) {
        return twitchService.getUsersPanelExtensions(token);
    }
}

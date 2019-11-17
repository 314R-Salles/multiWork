package com.psalles.multiWork.Twitch.Controller;

import com.psalles.multiWork.Twitch.Model.*;
import com.psalles.multiWork.Twitch.Service.TwitchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(description = "Calls Twitch API for basic info on streams")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/twitch")
public class TwitchController {

    private final TwitchService twitchService;

    @Autowired
    public TwitchController(TwitchService twitchService) {
        this.twitchService = twitchService;
    }

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

    @ApiOperation("Get streaming status of {userId}'s subscription list")
    @GetMapping("/streams/user/{userId}")
    public List<LiveData> getStreamingStatus(@PathVariable String userId) throws IOException {
        List<String> subscriptions = twitchService.getSubscriptions(userId).stream().map(Subscription::getToName).collect(Collectors.toList());
        return twitchService.getStreamingStatus(subscriptions);
    }

    @ApiOperation("Get user videos")
    @GetMapping("/user/{userId}/videos")
    public List<Video> getVideos(@PathVariable String userId) throws IOException {
        return twitchService.getUserVideos(userId);
    }

    @ApiOperation("Get streaming status of {userId}'s subscription list but it's V2")
    @GetMapping("/streams/user/{userId}/2")
    public List<EnrichedStreamer> getStreamingStatus2(@PathVariable String userId) throws IOException {
        List<String> subscriptions = twitchService.getSubscriptions(userId).stream().map(Subscription::getToName).collect(Collectors.toList());
        List<Streamer> streamers = twitchService.getUsers(subscriptions);
        List<LiveData> lives = twitchService.getStreamingStatus(subscriptions);

        List<EnrichedStreamer> enrichedStreamers = new ArrayList<>();
        streamers.forEach(
                streamer ->
                        enrichedStreamers.add(
                                new EnrichedStreamer(streamer,
                                        lives.stream()
                                                .filter(liveData -> liveData.getUsername().equals(streamer.displayName))
                                                .findFirst()
                                                .orElse(null))));
        return enrichedStreamers;
    }

}

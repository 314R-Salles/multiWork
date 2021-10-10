package com.psalles.multiWork.twitter.controller;

import com.psalles.multiWork.twitter.Tweets;
import com.psalles.multiWork.twitter.TwitterUser;
import com.psalles.multiWork.twitter.service.TwitterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = " ", tags = "Twitter endpoints")
@RestController
@RequestMapping("/twitter")
public class TwitterController {

    private final TwitterService twitterService;

    @Autowired
    public TwitterController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @ApiOperation(value = "Get profile public properties")
    @GetMapping("/profile/{username}")
    public TwitterUser getUser(@PathVariable String username) {
        return twitterService.getProfile(username);
    }

    @ApiOperation(value = "Get last tweets of user")
    @GetMapping("/tweets/{username}")
    public Tweets getTweets(@PathVariable String username) {
        return twitterService.getTweets(username);
    }

}

package com.psalles.multiWork.twitter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TwitterCron {

    private TwitterService twitterService;

    @Autowired
    public TwitterCron(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @Scheduled(cron = "0 0/30 6-23 * * *")
    public void cacheUpdate() {
        twitterService.updateTweets("RERB");
    }

}

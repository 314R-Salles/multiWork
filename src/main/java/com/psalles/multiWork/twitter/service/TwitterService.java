package com.psalles.multiWork.twitter.service;

import com.psalles.multiWork.Commons.Client.BaseHttpClient;
import com.psalles.multiWork.twitter.Tweets;
import com.psalles.multiWork.twitter.TwitterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TwitterService {

    private final BaseHttpClient httpClient;
    private final String TWEET_URL = "https://twitter.com/account/status/id";
    @Value("${twitter.api.v2.tweets}")
    private String TWEETS_BASE_URL;
    @Value("${twitter.api.v2.user}")
    private String USER_BASE_URL;
    @Value("${twitter.bearer}")
    private String token;

    @Autowired
    public TwitterService(BaseHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public TwitterUser getProfile(String username) {
        return httpClient.makeCall(HttpMethod.GET, USER_BASE_URL + username, TwitterUser.class, null, getAuthHeaders());
    }

    @Scheduled(cron = "0 0 6-23 ? * *")
    public Tweets cacheUpdate() {
        return updateTweets("RERB");
    }

    @CachePut("tweets")
    public Tweets updateTweets(String username) {
        return getTweets(username);
    }

    @Cacheable("tweets")
    public Tweets getTweets(String username) {
        Tweets tweets = httpClient.makeCall(HttpMethod.GET, TWEETS_BASE_URL + "from:" + username + "&max_results=30", Tweets.class, null, getAuthHeaders());
//        Tweets tweets = httpClient.makeCall(Resources.toString(Resources.getResource("twitter_mock.json"), Charsets.UTF_8), Tweets.class);
        tweets.getData().forEach(tweet -> {
            tweet.setUrl(TWEET_URL.replace("account", username).replace("id", tweet.getId()));
            String problem = "❌";
            String warning = "⚠";
            String backToNormal = "✅";
            if (tweet.getText().contains(problem)) tweet.setLevel("problem");
            if (tweet.getText().contains(warning)) tweet.setLevel("warning");
            if (tweet.getText().contains(backToNormal)) tweet.setLevel("normal");
        });

        String lastLevel = tweets.getData().stream().map(Tweets.Tweet::getLevel).filter(Objects::nonNull).findFirst().orElse(null);
        tweets.setLastLevel(lastLevel);
        return tweets;
    }

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Accept", "application/json");
        return headers;
    }


}

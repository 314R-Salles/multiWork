package com.psalles.multiWork.Twitch.Service;

import com.psalles.multiWork.Commons.Client.BaseHttpClient;
import com.psalles.multiWork.Commons.Utils.ParameterStringBuilder;
import com.psalles.multiWork.Twitch.Model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class TwitchService {
    private final String STREAMS_BASE_URL;
    private final String USERS_BASE_URL;
    private final String VIDEOS_BASE_URL;
    private final String FOLLOWS_BASE_URL;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final BaseHttpClient httpClient;
    private String token;

    @Autowired
    public TwitchService(
            @Value("${twitch.url.streams_base_url}") String streamUrl,
            @Value("${twitch.url.users_base_url}") String userUrl,
            @Value("${twitch.url.videos_base_url}") String videoUrl,
            @Value("${twitch.url.follows_base_url}") String followsBaseUrl,
            BaseHttpClient httpClient) {
        this.STREAMS_BASE_URL = streamUrl;
        this.USERS_BASE_URL = userUrl;
        this.FOLLOWS_BASE_URL = followsBaseUrl;
        this.VIDEOS_BASE_URL = videoUrl;
        this.httpClient = httpClient;
    }

    /**********************************************************************
     XXXXXXXXXXXXXXX               V1            XXXXXXXXXXXXXXXXXXXXXXXXXX
     **********************************************************************/

    public List<LiveData> getStreamingStatus(List<String> ids) throws IOException {
        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("user_login", ids);
        LOGGER.debug(STREAMS_BASE_URL);
        String url = STREAMS_BASE_URL + ParameterStringBuilder.getParamsStringList(parameters);
        LiveDataResponse response = httpClient.makeCall(HttpMethod.GET, url, LiveDataResponse.class, null, getAuthHeaders());
        return response.getData();
    }

    public List<Subscription> getSubscriptions(String id) throws IOException {
        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("from_id", Collections.singletonList(id));
        LOGGER.debug(FOLLOWS_BASE_URL);
        String url = FOLLOWS_BASE_URL + ParameterStringBuilder.getParamsStringList(parameters);
        SubscriptionResponse response = httpClient.makeCall(HttpMethod.GET, url, SubscriptionResponse.class, null, getAuthHeaders());
        return response.getData();
    }

    public User getUserInfoFromAuthToken() {
        LOGGER.debug(USERS_BASE_URL);
        UserResponse response = httpClient.makeCall(HttpMethod.GET, USERS_BASE_URL, UserResponse.class, null, getAuthHeaders());
        return response.getData().get(0);
    }

    public void saveToken(String token) {
        this.token = token;
        LOGGER.debug("Saved token: " + token);
    }


    /**********************************************************************
     XXXXXXXXXXXXXXX               V2            XXXXXXXXXXXXXXXXXXXXXXXXXX
     **********************************************************************/

    public List<Streamer> getUsers(List<String> usernames) throws IOException {
        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("login", usernames);
        LOGGER.debug(USERS_BASE_URL);
        String url = USERS_BASE_URL + ParameterStringBuilder.getParamsStringList(parameters);
        StreamerResponse response = httpClient.makeCall(HttpMethod.GET, url, StreamerResponse.class, null, getAuthHeaders());
        return response.getData();
    }

    public List<Video> getUserVideos(String userId) throws IOException {
        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("user_id", Collections.singletonList(userId));
        LOGGER.debug(VIDEOS_BASE_URL);
        String url = VIDEOS_BASE_URL + ParameterStringBuilder.getParamsStringList(parameters);
        VideoResponse response = httpClient.makeCall(HttpMethod.GET, url, VideoResponse.class, null, getAuthHeaders());
        return response.getData();
    }

    /**********************************************************************
     XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
     **********************************************************************/

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }

}

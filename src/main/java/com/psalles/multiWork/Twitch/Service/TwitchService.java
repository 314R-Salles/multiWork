package com.psalles.multiWork.Twitch.Service;

import com.psalles.multiWork.Commons.Client.BaseHttpClient;
import com.psalles.multiWork.Commons.Utils.ParameterStringBuilder;
import com.psalles.multiWork.Twitch.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class TwitchService {
    private final String STREAMS_BASE_URL;
    private final String USERS_BASE_URL;
    private final String VIDEOS_BASE_URL;
    private final String FOLLOWS_BASE_URL;
    private final String EXTENSIONS_BASE_URL;
    private final BaseHttpClient httpClient;
    private String token;

    @Autowired
    public TwitchService(
            @Value("${twitch.url.streams_base_url}") String streamUrl,
            @Value("${twitch.url.users_base_url}") String userUrl,
            @Value("${twitch.url.videos_base_url}") String videoUrl,
            @Value("${twitch.url.follows_base_url}") String followsBaseUrl,
            @Value("${twitch.url.extensions_base_url}") String extensionsBaseUrl,
            BaseHttpClient httpClient) {
        this.STREAMS_BASE_URL = streamUrl;
        this.USERS_BASE_URL = userUrl;
        this.FOLLOWS_BASE_URL = followsBaseUrl;
        this.EXTENSIONS_BASE_URL = extensionsBaseUrl;
        this.VIDEOS_BASE_URL = videoUrl;
        this.httpClient = httpClient;
    }

    public User getUserInfoFromAuthToken() {
        UserResponse response = httpClient.makeCall(HttpMethod.GET, USERS_BASE_URL, UserResponse.class, null, getAuthHeaders());
        return response.getData().get(0);
    }

    public void saveToken(String token) {
        this.token = token;
    }

    public List<Video> getUserVideos(String userId) throws IOException {
        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("user_id", Collections.singletonList(userId));
        String url = VIDEOS_BASE_URL + ParameterStringBuilder.getParamsStringList(parameters);
        VideoResponse response = httpClient.makeCall(HttpMethod.GET, url, VideoResponse.class, null, getAuthHeaders());
        return response.getData();
    }

    public List<EnrichedStreamer> getStreamingStatus(String userId) throws IOException {
        List<String> subscriptions = getSubscriptions(userId).stream().map(Subscription::getToName).collect(Collectors.toList());
        List<Streamer> streamers = getUsers(subscriptions);
        List<LiveData> streams = getStreams(subscriptions);

        List<EnrichedStreamer> enrichedStreamers = new ArrayList<>();
        streamers.forEach(
                streamer ->
                        enrichedStreamers.add(
                                new EnrichedStreamer(streamer,
                                        streams.stream()
                                                .filter(liveData -> liveData.getUsername().equals(streamer.displayName))
                                                .findFirst()
                                                .orElse(null))));
        return enrichedStreamers;
    }


    public Map<String, List<Extension>> getUsersPanelExtensions(String userId) throws IOException {
        List<String> subscriptions = getSubscriptions(userId).stream().map(Subscription::getToId).collect(toList());
        Map<String, List<Extension>> extensionMap = new HashMap<>();

        CompletableFuture[] futures = new CompletableFuture[subscriptions.size()];
        for (int i = 0; i < subscriptions.size(); i++) {
            String subscription = subscriptions.get(i);
            futures[i] = CompletableFuture.runAsync(() ->
                    {
                        ExtensionResponse response = httpClient.makeCall(HttpMethod.GET, EXTENSIONS_BASE_URL + "user_id=" + subscription, ExtensionResponse.class, null, getAuthHeaders());
                        extensionMap.put(subscription,
                                response.getData().getPanel().entrySet().stream().map(Map.Entry::getValue).filter(extension -> extension.getId() != null).collect(toList()));
                    }
            );
        }
        CompletableFuture.allOf(futures).join();
        return extensionMap;
    }

    /**********************************************************************
     XXXXXXXXXXXXXXXXXXXXXXX       PRIVATE     XXXXXXXXXXXXXXXXXXXXXXXXXXXX
     **********************************************************************/

    private List<Streamer> getUsers(List<String> usernames) throws IOException {
        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("login", usernames);
        String url = USERS_BASE_URL + ParameterStringBuilder.getParamsStringList(parameters);
        StreamerResponse response = httpClient.makeCall(HttpMethod.GET, url, StreamerResponse.class, null, getAuthHeaders());
        return response.getData();
    }

    private List<LiveData> getStreams(List<String> ids) throws IOException {
        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("user_login", ids);
        String url = STREAMS_BASE_URL + ParameterStringBuilder.getParamsStringList(parameters);
        LiveDataResponse response = httpClient.makeCall(HttpMethod.GET, url, LiveDataResponse.class, null, getAuthHeaders());
        return response.getData();
    }

    private List<Subscription> getSubscriptions(String id) throws IOException {
        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("from_id", Collections.singletonList(id));
        String url = FOLLOWS_BASE_URL + ParameterStringBuilder.getParamsStringList(parameters);
        SubscriptionResponse response = httpClient.makeCall(HttpMethod.GET, url, SubscriptionResponse.class, null, getAuthHeaders());
        return response.getData();
    }


    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }

}

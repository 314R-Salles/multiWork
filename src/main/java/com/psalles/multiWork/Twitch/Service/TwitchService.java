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

    @Value("${twitch.url.streams_base_url}")
    private String STREAMS_BASE_URL;

    @Value("${twitch.url.users_base_url}")
    private String USERS_BASE_URL;

    @Value("${twitch.url.videos_base_url}")
    private String VIDEOS_BASE_URL;

    @Value("${twitch.url.games_base_url}")
    private String GAME_BASE_URL;

    @Value("${twitch.url.follows_base_url}")
    private String FOLLOWS_BASE_URL;

    @Value("${twitch.url.extensions_base_url}")
    private String EXTENSIONS_BASE_URL;

    private final BaseHttpClient httpClient;

    @Autowired
    public TwitchService(BaseHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public User getUserInfoFromAuthToken(String token) {
        UserResponse response = httpClient.makeCall(HttpMethod.GET, USERS_BASE_URL, UserResponse.class, null, getAuthHeaders(token));
        return response.getData().get(0);
    }

    public List<Video> getUserVideos(String token, String userId) throws IOException {
        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("user_id", Collections.singletonList(userId));
        parameters.put("first", Collections.singletonList("100"));
        String url = VIDEOS_BASE_URL + ParameterStringBuilder.getParamsStringList(parameters);
        VideoResponse response = httpClient.makeCall(HttpMethod.GET, url, VideoResponse.class, null, getAuthHeaders(token));
        return response.getData();
    }

    public List<EnrichedStreamer> getStreamingStatus(String token, String userId) throws IOException {
        List<String> subscriptions = getSubscriptions(token, userId).stream().map(Subscription::getToName).collect(Collectors.toList());
        List<Streamer> streamers = getUsers(token, subscriptions);

        List<LiveData> streams = getStreams(token, subscriptions);
        List<EnrichedLiveData> enrichedStreams = completeStreams(token, streams);

        List<EnrichedStreamer> enrichedStreamers = new ArrayList<>();
        streamers.forEach(
                streamer ->
                        enrichedStreamers.add(
                                new EnrichedStreamer(streamer,
                                        enrichedStreams.stream()
                                                .filter(liveData -> liveData.getUsername().equals(streamer.displayName))
                                                .findFirst()
                                                .orElse(null))));
        return enrichedStreamers;
    }


    public Map<String, List<Extension>> getUsersPanelExtensions(String token, String userId) throws IOException {
        List<String> subscriptions = getSubscriptions(token, userId).stream().map(Subscription::getToId).collect(toList());
        Map<String, List<Extension>> extensionMap = new HashMap<>();

        CompletableFuture[] futures = new CompletableFuture[subscriptions.size()];
        for (int i = 0; i < subscriptions.size(); i++) {
            String subscription = subscriptions.get(i);
            futures[i] = CompletableFuture.runAsync(() ->
                    {
                        ExtensionResponse response = httpClient.makeCall(HttpMethod.GET, EXTENSIONS_BASE_URL + "user_id=" + subscription, ExtensionResponse.class, null, getAuthHeaders(token));
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

    private List<Streamer> getUsers(String token, List<String> usernames) throws IOException {
        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("login", usernames);
        String url = USERS_BASE_URL + ParameterStringBuilder.getParamsStringList(parameters);
        StreamerResponse response = httpClient.makeCall(HttpMethod.GET, url, StreamerResponse.class, null, getAuthHeaders(token));
        return response.getData();
    }

    private List<LiveData> getStreams(String token, List<String> ids) throws IOException {
        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("user_login", ids);
        String url = STREAMS_BASE_URL + ParameterStringBuilder.getParamsStringList(parameters);
        LiveDataResponse response = httpClient.makeCall(HttpMethod.GET, url, LiveDataResponse.class, null, getAuthHeaders(token));
        return response.getData();
    }

    private List<Subscription> getSubscriptions(String token, String id) throws IOException {
        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("from_id", Collections.singletonList(id));
        parameters.put("first", Collections.singletonList("100"));
        String url = FOLLOWS_BASE_URL + ParameterStringBuilder.getParamsStringList(parameters);
        SubscriptionResponse response = httpClient.makeCall(HttpMethod.GET, url, SubscriptionResponse.class, null, getAuthHeaders(token));
        return response.getData();
    }

    private List<EnrichedLiveData> completeStreams(String token, List<LiveData> streams) throws IOException {
        HashMap<String, List<String>> parameters = new HashMap<>();
        parameters.put("id", streams.stream().map(LiveData::getGameId).collect(toList()));
        String url = GAME_BASE_URL + ParameterStringBuilder.getParamsStringList(parameters);
        GameResponse response = httpClient.makeCall(HttpMethod.GET, url, GameResponse.class, null, getAuthHeaders(token));

        return streams.stream().map(stream ->
                new EnrichedLiveData(stream,
                response.getData().stream().filter(game -> game.getId().equals(stream.getGameId())).findFirst().get())).collect(toList());

    }



    private HttpHeaders getAuthHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }

}

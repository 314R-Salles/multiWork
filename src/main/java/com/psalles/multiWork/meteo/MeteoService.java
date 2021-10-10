package com.psalles.multiWork.meteo;

import com.psalles.multiWork.Commons.Client.BaseHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class MeteoService {

    private final BaseHttpClient httpClient;

    @Value("${meteo.url}")
    private String METEO_BASE_URL;

    @Autowired
    public MeteoService(BaseHttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public MeteoResponse getMeteo(String lat, String lon) {
        String url = METEO_BASE_URL.replace("{lat}", lat).replace("{lon}", lon);
        return httpClient.makeCall(HttpMethod.GET, url, MeteoResponse.class, null, null);
    }
}

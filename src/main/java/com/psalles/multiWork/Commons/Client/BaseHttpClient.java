package com.psalles.multiWork.Commons.Client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.EMPTY;

@Component
public class BaseHttpClient {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public <R> R makeCall(HttpMethod method,
                          String url,
                          Class<R> responseClass,
                          Object request,
                          HttpHeaders extraHeaders) {

        String jsonRequest = formatRequestToJson(request);

        HttpStatus httpStatus;
        String responseBody;
        try {
            ResponseEntity<String> responseEntity = sendHttpRequest(method, url, extraHeaders, jsonRequest);
            httpStatus = responseEntity.getStatusCode();
            responseBody = responseEntity.hasBody() ? responseEntity.getBody() : EMPTY;

            return parseResponseFromJson(responseBody, responseClass);

        } catch (HttpClientErrorException | HttpServerErrorException restEx) {
            httpStatus = restEx.getStatusCode();
            responseBody = restEx.getResponseBodyAsString();
            LOGGER.debug("Following ERROR response has been received [{}] : {}", httpStatus, responseBody);
            throw restEx;
        } catch (Exception e) {
            LOGGER.debug("Error while parsing response", e);
            throw e;
        }
    }

    private String formatRequestToJson(Object request) {
        try {
            return mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <R> R parseResponseFromJson(String responseBody, Class<R> responseClass) {
        try {
            return mapper.readValue(responseBody, responseClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private ResponseEntity<String> sendHttpRequest(HttpMethod method, String url, HttpHeaders extraHeaders,
                                                   String jsonRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (extraHeaders != null) {
            headers.putAll(extraHeaders);
        }

        final HttpEntity<String> entity;
        if (jsonRequest != null && !jsonRequest.isEmpty()) {
            entity = new HttpEntity<>(jsonRequest, headers);
        } else {
            entity = new HttpEntity<>(headers);
        }

        return restTemplate.exchange(url, method, entity, String.class);
    }

}
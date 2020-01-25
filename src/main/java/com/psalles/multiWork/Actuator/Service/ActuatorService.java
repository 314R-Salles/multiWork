package com.psalles.multiWork.Actuator.Service;

import com.psalles.multiWork.Actuator.Model.Bean;
import com.psalles.multiWork.Actuator.Model.BeansResponse;
import com.psalles.multiWork.Actuator.Model.Context;
import com.psalles.multiWork.Commons.Client.BaseHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ActuatorService {


    private final BaseHttpClient httpClient;
    private final String ACTUATOR_BASE_URL;

    @Autowired
    public ActuatorService(
            @Value("${actuator.base_url}") String actuatorBaseUrl,
            BaseHttpClient httpClient
    ) {
        this.ACTUATOR_BASE_URL = actuatorBaseUrl;
        this.httpClient = httpClient;

    }

    public List<Bean> getBeans() {
        String url = ACTUATOR_BASE_URL + "/beans";
        BeansResponse response = httpClient.makeCall(HttpMethod.GET, url, BeansResponse.class, null, null);
        return convert(response.getContexts());
    }

    private List<Bean> convert(Map<String, Context> contexts) {
        List<Bean> result = new ArrayList<>();
        contexts.forEach((contextName, contextContent) ->
                contextContent.getBeans().forEach((beanName, bean) -> {
                    bean.setName(beanName);
                    bean.setContext(contextName);
                    result.add(bean);
                }));
        return result;
    }


}

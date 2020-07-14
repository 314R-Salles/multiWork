package com.psalles.multiWork.Poc.Websocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectFourPayload {
    private String uuid;
    private String xpos;
    private boolean replay;
}


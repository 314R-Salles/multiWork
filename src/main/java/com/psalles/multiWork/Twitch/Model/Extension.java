package com.psalles.multiWork.Twitch.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Extension {

    boolean active;
    String id;
    String version;
    String name;
}

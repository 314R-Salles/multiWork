package com.psalles.multiWork.Actuator.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bean {

    String name;
    List<String> aliases;
    String scope;
    String type;
    String resource;
    List<String> dependencies;
}

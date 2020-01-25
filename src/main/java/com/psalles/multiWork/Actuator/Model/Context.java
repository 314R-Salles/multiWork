package com.psalles.multiWork.Actuator.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Context {
    Map<String, Bean> beans;
    String parentId;
}

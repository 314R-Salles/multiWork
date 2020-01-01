package com.psalles.multiWork.Actuator.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class BeansResponse {
    Context contexts;
}

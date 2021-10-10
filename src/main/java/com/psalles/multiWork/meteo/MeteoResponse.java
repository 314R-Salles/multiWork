package com.psalles.multiWork.meteo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeteoResponse {
    private String timezone;
    private Current current;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Current {
        private float temp;
        private float feels_like;
        private List<Weather> weather;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;


        @JsonSetter("icon")
        public void setIconUrl(String icon) {
            this.icon = "http://openweathermap.org/img/wn/" + icon + "@2x.png";
        }


    }
}


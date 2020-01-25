package com.psalles.multiWork.Home.Service;

import com.psalles.multiWork.Home.Model.Video;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@ConfigurationProperties(prefix = "home")
public class HomeService {
    private List<Video> videos = new ArrayList<>();


    // TODO, à approfondir
    @PostConstruct
    public void init() {
        for(Video current : this.getVideos()) {
            System.out.println(current.getUrl());
        }
        this.getVideos();
    }

    public List<Video> getVideos() {
        return this.videos;
    }


}

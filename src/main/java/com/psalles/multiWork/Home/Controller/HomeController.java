package com.psalles.multiWork.Home.Controller;

import com.psalles.multiWork.Home.Model.Video;
import com.psalles.multiWork.Home.Service.HomeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RefreshScope
@RestController
@CrossOrigin(origins = "http://51.178.84.104")
@RequestMapping("/home")
public class HomeController {

    private HomeService homeService;

    @Value("${application.messages.title:default}")
    private Object message;

    HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/message")
    Object getMessage() {
        return this.message;
    }

    @GetMapping("/videos")
    List<Video> getVideos() {
        return this.homeService.getVideos();
    }


}

package com.psalles.multiWork.Home.Controller;

import com.psalles.multiWork.Home.Model.Video;
import com.psalles.multiWork.Home.Service.HomeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RefreshScope
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/home")
public class HomeController {

    private HomeService homeService;

    @Value("${application.messages.title}")
    private Object message;

    HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @RequestMapping("/message")
    Object getMessage() {
        return this.message;
    }

    @RequestMapping("/videos")
    List<Video> getVideos() {
        return this.homeService.getVideos();
    }


}

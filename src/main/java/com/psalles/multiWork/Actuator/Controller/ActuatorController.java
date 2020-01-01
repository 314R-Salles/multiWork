package com.psalles.multiWork.Actuator.Controller;

import com.psalles.multiWork.Actuator.Model.Bean;
import com.psalles.multiWork.Actuator.Service.ActuatorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "Calls Spring Boot Actuators for metrics data")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/actuators")
public class ActuatorController {

    private final ActuatorService actuatorService;

    @Autowired
    public ActuatorController(ActuatorService actuatorService){
        this.actuatorService = actuatorService;
    }


    @GetMapping("/beans")
    public List<Bean> getBeans()  {
        return actuatorService.getBeans();
    }

}

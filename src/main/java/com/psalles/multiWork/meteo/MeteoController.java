package com.psalles.multiWork.meteo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/meteo")
@Api(description = " ", tags = "Meteo endpoints")
public class MeteoController {

    @Autowired
    private MeteoService meteoService;

    @ApiOperation(value = "Get meteo from some coordinates")
    @GetMapping("/lat/{lat}/lon/{lon}")
    public MeteoResponse getMeteo(@PathVariable String lat, @PathVariable String lon) {
        return meteoService.getMeteo(lat, lon);
    }

}

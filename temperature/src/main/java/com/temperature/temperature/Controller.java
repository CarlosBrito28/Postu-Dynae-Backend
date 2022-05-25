package com.temperature.temperature;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {

    private final RestTemplate restTemplate;

    @Autowired
    public Controller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/temperatures-calculate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getApi(@RequestParam String from, @RequestParam String to,
            @RequestParam String temperature) {

        String url = "http://data-env.eba-pwemrg4q.us-east-1.elasticbeanstalk.com/data/sensorElement/8/measurement?from="
                + from + "&to=" + to + "&timeUnit=SEC";

        ResponseEntity<ResponseSensor[]> response = restTemplate.getForEntity(url, ResponseSensor[].class);

        ResponseSensor[] responseSensors = response.getBody();

        Float majorTemperature = null, minorTemperature = null;

        if (responseSensors.length > 0) {

            majorTemperature = minorTemperature = responseSensors[0].getMagnitude();

            for (int i = 0; i < responseSensors.length; i++) {

                if (responseSensors[i].getMagnitude() > majorTemperature) {

                    majorTemperature = responseSensors[i].getMagnitude();
                }

                if (responseSensors[i].getMagnitude() < minorTemperature) {
                    minorTemperature = responseSensors[i].getMagnitude();
                }
            }
        }

        HashMap<String, Float> returnValue = new HashMap<>();

        returnValue.put("majorTemperature", majorTemperature);

        returnValue.put("minorTemperature", minorTemperature);

        return new ResponseEntity<Object>(returnValue, HttpStatus.OK);
    }

}

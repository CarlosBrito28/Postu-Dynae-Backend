package com.temperature.temperature;

import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/temperaturescalculate")
    public Object getApi(@RequestParam String from, @RequestParam String to, @RequestParam String temperature){
        String url = "http://data-env.eba-pwemrg4q.us-east-1.elasticbeanstalk.com/data/sensorElement/8/measurement?from="+ from +"&to=" + to + "&timeUnit=SEC";
        Object response =  restTemplate.getForObject(url, Object.class);
        //Definir un pojo para la respuesta

        //Calcular temperatura minima

        //Calcular temperatura maxima

        //Cantidad de
        //segundos que en ese rango de fechas la temperatura estuvo por sobre la temperatura
        //objetivo indicada por el usuario
        
        
        return response;
    }
    
}

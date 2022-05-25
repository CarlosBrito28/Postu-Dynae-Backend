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
        // Declaro url de la api
        String url = "http://data-env.eba-pwemrg4q.us-east-1.elasticbeanstalk.com/data/sensorElement/8/measurement?from="
                + from + "&to=" + to + "&timeUnit=SEC";
        // Realizo peticion Get a la api y transformo a clase ResponseSensor
        ResponseEntity<ResponseSensor[]> response = restTemplate.getForEntity(url, ResponseSensor[].class);
        // Obtengo los valores de respuesta de api y lo guardo en un arreglo de
        // responseSensor
        ResponseSensor[] responseSensors = response.getBody();
        // Declaro en null las variables para guardar las temperaturas minimas y maximas
        Float majorTemperature = null, minorTemperature = null;
        // Verifico que el api haya traido elementos
        if (responseSensors.length > 0) {
            // Asigno la temperatura del primer elemento de la api como la mayor y la menor
            majorTemperature = minorTemperature = responseSensors[0].getMagnitude();
            // Recorrer arreglo de sensors y calcular temperatura minima y maxima
            for (int i = 0; i < responseSensors.length; i++) {
                // Pregunto si la temperatura en esta posicion es mayor que la temperatura
                // asignada como mayor
                if (responseSensors[i].getMagnitude() > majorTemperature) {
                    // Si es verdadero asigno esta temperatura como mayor
                    majorTemperature = responseSensors[i].getMagnitude();
                }
                // Pregunto si la temperatura en esta posicion es menor que la temperatura
                // asignada como menor
                if (responseSensors[i].getMagnitude() < minorTemperature) {
                    minorTemperature = responseSensors[i].getMagnitude();
                }
            }
        }
        // Declaro un hash map para retornar la respuesta
        HashMap<String, Float> returnValue = new HashMap<>();
        // Agrego la posicion majorTemperature a la respuesta
        returnValue.put("majorTemperature", majorTemperature);
        // Agrego la posicion minorTemperature a la respuesta
        returnValue.put("minorTemperature", minorTemperature);
        // Retorno respuesta con un status OK
        return new ResponseEntity<Object>(returnValue, HttpStatus.OK);
    }

}

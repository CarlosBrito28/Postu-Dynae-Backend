package com.temperature.temperature;

import java.util.Date;
import lombok.Data;


@Data
public class ResponseSensor {
    
    private Long id;
    private String sensorElementId;
    private Float magnitude;
    private Float variation;
    private Date timestamp; 
}

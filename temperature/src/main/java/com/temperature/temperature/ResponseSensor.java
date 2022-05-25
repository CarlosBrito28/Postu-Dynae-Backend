package com.temperature.temperature;

import java.util.Date;

public class ResponseSensor {

    private Long id;
    private String sensorElementId;
    private Float magnitude;
    private Float variation;
    private Date timestamp;

    public Long getId() {
        return id;
    }

    public String getSensorElementId() {
        return sensorElementId;
    }

    public Float getMagnitude() {
        return magnitude;
    }

    public Float getVariation() {
        return variation;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}

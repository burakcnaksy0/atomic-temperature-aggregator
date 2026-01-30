package com.burakcanaksoy.atomictemperatureaggregator.dto;

import lombok.Data;

@Data
public class TemperatureRequest {
    private int value;
    private String scale;
}
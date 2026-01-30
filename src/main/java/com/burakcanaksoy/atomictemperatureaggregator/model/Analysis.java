package com.burakcanaksoy.atomictemperatureaggregator.model;

import lombok.Data;

@Data
public class Analysis {
    private boolean isFreezing;
    private boolean isBoiling;
    private String temperatureLevel;

    public Analysis(boolean isFreezing,boolean isBoiling,String temperatureLevel){
        this.isFreezing = isFreezing;
        this.isBoiling = isBoiling;
        this.temperatureLevel = temperatureLevel;
    }
}
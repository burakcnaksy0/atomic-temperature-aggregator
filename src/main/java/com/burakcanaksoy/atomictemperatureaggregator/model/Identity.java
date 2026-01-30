package com.burakcanaksoy.atomictemperatureaggregator.model;

import lombok.Data;

@Data
public class Identity {
    private int inputValue;
    private String inputScale;
    private int convertedValue;
    private String convertedScale;

    public Identity(int inputValue,String inputScale,int convertedValue,String convertedScale){
        this.inputValue = inputValue;
        this.inputScale = inputScale;
        this.convertedValue = convertedValue;
        this.convertedScale = convertedScale;
    }
}

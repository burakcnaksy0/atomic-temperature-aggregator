package com.burakcanaksoy.atomictemperatureaggregator.model;

import lombok.Data;

@Data
public class Validation {
    private int reverseConvertedValue;
    private boolean isAccurate;

    public Validation(int reverseConvertedValue,boolean isAccurate){
        this.reverseConvertedValue = reverseConvertedValue;
        this.isAccurate = isAccurate;
    }
}

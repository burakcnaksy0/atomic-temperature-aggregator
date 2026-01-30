package com.burakcanaksoy.atomictemperatureaggregator.dto;

import com.burakcanaksoy.atomictemperatureaggregator.model.Analysis;
import com.burakcanaksoy.atomictemperatureaggregator.model.Identity;
import com.burakcanaksoy.atomictemperatureaggregator.model.Validation;
import lombok.Data;

@Data
public class TemperatureResponse {
    private Identity identity;
    private Analysis analysis;
    private Validation validation;
}

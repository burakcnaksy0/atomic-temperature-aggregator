package com.burakcanaksoy.atomictemperatureaggregator.service;

import com.burakcanaksoy.atomictemperatureaggregator.dto.TemperatureRequest;
import com.burakcanaksoy.atomictemperatureaggregator.dto.TemperatureResponse;

public interface TemperatureService {
    TemperatureResponse convertTemperature(TemperatureRequest request);
}

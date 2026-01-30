package com.burakcanaksoy.atomictemperatureaggregator.service.impl;

import com.burakcanaksoy.atomictemperatureaggregator.dto.TemperatureRequest;
import com.burakcanaksoy.atomictemperatureaggregator.dto.TemperatureResponse;
import com.burakcanaksoy.atomictemperatureaggregator.model.Analysis;
import com.burakcanaksoy.atomictemperatureaggregator.model.Identity;
import com.burakcanaksoy.atomictemperatureaggregator.model.Validation;
import com.burakcanaksoy.atomictemperatureaggregator.service.TemperatureService;
import com.burakcanaksoy.tempconvert.wsdl.TempConvertSoap;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class TemperatureServiceImpl implements TemperatureService {

    private final TempConvertSoap soapClient;

    public TemperatureServiceImpl(TempConvertSoap soapClient) {
        this.soapClient = soapClient;
    }

    @Override
    public TemperatureResponse convertTemperature(TemperatureRequest request) {
        String inputValueStr = String.valueOf(request.getValue());
        String inputScale = request.getScale();

        String convertedValueStr;
        String convertedScale;

        if ("C".equalsIgnoreCase(inputScale)) {
            convertedValueStr = soapClient.celsiusToFahrenheit(inputValueStr);
            convertedScale = "F";
        } else if ("F".equalsIgnoreCase(inputScale)) {
            convertedValueStr = soapClient.fahrenheitToCelsius(inputValueStr);
            convertedScale = "C";
        } else {
            throw new IllegalArgumentException(
                    "Desteklenmeyen sıcaklık birimi: " + inputScale + ". Lütfen 'C' veya 'F' kullanın.");
        }

        if (convertedValueStr == null || convertedValueStr.equals("Error")) {
            throw new RuntimeException("SOAP servisi dönüşüm hatası döndürdü.");
        }

        int inputValue = request.getValue();
        int convertedValue = Integer.parseInt(convertedValueStr);

        Identity identity = new Identity(inputValue, inputScale, convertedValue, convertedScale);

        CompletableFuture<Validation> validationFuture = CompletableFuture.supplyAsync(
                () -> validateConversion(inputValue, convertedValue, convertedScale));

        CompletableFuture<Analysis> analysisFuture = CompletableFuture.supplyAsync(
                () -> analyzeTemperature(inputValue, inputScale, convertedValue));

        CompletableFuture.allOf(validationFuture, analysisFuture).join();

        try {
            TemperatureResponse response = new TemperatureResponse();
            response.setIdentity(identity);
            response.setValidation(validationFuture.get());
            response.setAnalysis(analysisFuture.get());

            return response;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Paralel işlemler sırasında hata oluştu: " + e.getMessage());
        }
    }

    private Validation validateConversion(int originalValue, int convertedValue, String convertedScale) {
        String reverseInputStr = String.valueOf(convertedValue);
        String reverseResultStr;

        if ("C".equalsIgnoreCase(convertedScale)) {
            reverseResultStr = soapClient.celsiusToFahrenheit(reverseInputStr);
        } else {
            reverseResultStr = soapClient.fahrenheitToCelsius(reverseInputStr);
        }

        if (reverseResultStr == null || reverseResultStr.equals("Error")) {
            throw new RuntimeException("Validation SOAP call failed.");
        }

        int reverseResult = Integer.parseInt(reverseResultStr);

        boolean isAccurate = (reverseResult == originalValue);

        return new Validation(reverseResult, isAccurate);
    }

    private Analysis analyzeTemperature(int inputValue, String inputScale, int convertedValue) {
        int celsiusValue;
        if ("C".equalsIgnoreCase(inputScale)) {
            celsiusValue = inputValue;
        } else {
            celsiusValue = convertedValue; // Converted was F->C, so it is C
        }

        boolean isFreezing = celsiusValue <= 0;
        boolean isBoiling = celsiusValue >= 100;
        String level;

        if (celsiusValue <= 0) {
            level = "Freezing";
        } else if (celsiusValue <= 20) {
            level = "Cold";
        } else if (celsiusValue <= 35) {
            level = "Warm";
        } else {
            level = "Hot";
        }

        return new Analysis(isFreezing, isBoiling, level);
    }
}

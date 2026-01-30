package com.burakcanaksoy.atomictemperatureaggregator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.burakcanaksoy.tempconvert.wsdl.TempConvert;
import com.burakcanaksoy.tempconvert.wsdl.TempConvertSoap;

@Configuration
public class SoapClientConfig {

    @Bean
    public TempConvertSoap tempConvertSoap() {
        TempConvert service = new TempConvert();
        return service.getTempConvertSoap();
    }
}

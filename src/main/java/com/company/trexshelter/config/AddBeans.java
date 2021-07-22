package com.company.trexshelter.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddBeans {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}

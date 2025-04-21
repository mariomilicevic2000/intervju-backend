package com.ht.interview.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public SimpleModule trimmingModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new StringTrimmingDeserializer());
        System.out.println("TRIMMING MODULE");
        return module;
    }
}

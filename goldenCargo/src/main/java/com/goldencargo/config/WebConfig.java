package com.goldencargo.config;

import com.goldencargo.component.DateConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final DateConverter dateConverter;

    public WebConfig(DateConverter dateConverter) {
        this.dateConverter = dateConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(dateConverter);
    }
}

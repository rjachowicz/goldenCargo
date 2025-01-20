package com.goldencargo.config;

import com.goldencargo.component.DateConverter;
import com.goldencargo.service.DropboxService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    String token = "sl.CEuUzbmTizUOwC4GAl7h84RDckHQXy_IkiJrse5TLofCEeUkj7zvsfnvFk_dY17wLzDwttlk8vf9NTLrGQq0hVFKhcg17IxA6qKwZviIGKWb-rex0hNW6yI2FS8O86OGMfnva9JDDPx_nrnI1s59HnQ";

    private final DateConverter dateConverter;

    public WebConfig(DateConverter dateConverter) {
        this.dateConverter = dateConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(dateConverter);
    }

    @Bean
    public DropboxService dropboxService() {
        return new DropboxService(token);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Object handler) {
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.setHeader("Pragma", "no-cache");
                response.setDateHeader("Expires", 0);
                return true;
            }
        });
    }
}

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
        return new DropboxService("sl.u.AFc5wCHxcbh1oNfhLJOX9-_I1ndRguaUAymgySYtq6_dZCwvUM1JwJyODbtcvkE0SU1IFuY20difGCgfs-pKVlKHKcdYKBt6sTmvruPqZNmZJGg5l0tLs6t-sg0n108asYrqQ0ymq2C8yTq5CYnrzZI_QNHgQgmwFGsOre1IusNzPTNFi8BdB6mSfMb6m94WfBoTO3O4823NqrVMKPxZa3hz6FBmm6G0-FYgsCSCNPD3w2ONeE6VrKn8WoYbQXAPyN0KpQ4lFs3yTxiQbTv7XRTOlPzoHSmjz2eE0O5OrxqS_0rU5pDUp2XR5QPB22s7DNqT_UHR0qirUCUtbEYqIcY8EM_bB2kXYEANXWmDMUn8Ce_MKYqiknnyByD--LiDkjy7gMCn1G-D-7dwJTZwb728F10AfxQWJpfgzxlSvqFH8LaI-llBpl3zZmy_s2NbIUJwrLdinnJ3c97keyn5q3DtJ_GqkeaBtaPTFcm32N0mccfwEOjyFMiZKGP_0GqCjQxK2trrcwohiWrPcLeLfDobxvE3M7DhfY9xZDaxo2VAhYIH0OKF2Pg_tKH0NEPrcLznomuLWiIgSD2iX3NYYajM6pPfpCoiNlousmTpgVj1I31jDlRXm4aQjtG3_7XT_MU1VMSdynDQ7elpjW9vh0ntoOmc90TFRYaUfQnzWVM9fYu9o2El93kyoMYCGBcmtkoKJxE4fn4QSB2Q0MLqwpVQZGh4lLK3YovlFSg3LOqo0buqbM8vmxH2_DVtzfpj5VlTng-fWGVtc3QLJMx6jHglyseCbsgKhFk9v5s5G1i0OdlpCNlL1Wn7DJX4DS9lrmOgJoPJuMDCGzXVNVxN8JXfgDC21KVxYV-NyPAgaWF15THDVBrtnIPzbXmrpQTcSQ3cBNBU4J3l4TpkG_TRdoXa9qYgOF2kT5kAqNYKYmSxnOP293GMYcSxvIqiVB3wCS71lnGFrp7XaYjxVJ4uwcEVCyfy9pc5-7U965WtOBXbxaFpo8IwZTq9HbfxO8U-xnV6oB7EneV_ZSbmijhLQTuvMtUpjfpXHlE37-jLKo3lzaprdbfecX5QBVWEwfkrRwRZOqGoZ9FBqI8Y3LDOncZxk_aH1qBo34Y44S7w5dZS1pSbp6YFoRDs9kIXHmW3ArWMTbJguCbU7nKmcQMMHAOICuElWaCAtOHIUeQqbdJ0YC4hFbvtQOAxwPT1GOO_IPSazL9-wTi-ntxiSmJO1uZtOo24WlpJxLMXhBGCNrGm5e-EzvjZ4o6bngygDyEKCFCszSHMa5boGhB0pYax969f4341it8NLNngrb3DZP46x08yNX8SXJMTuEMG7J69mwasYIT5rAqN8M5R9vTI7QxZ1ZWz0CgYetU7eBkqSzXpJiAQun1eIN25T5ec4HXrzBHSC9okb2frCsmHK_g0fiWb");
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

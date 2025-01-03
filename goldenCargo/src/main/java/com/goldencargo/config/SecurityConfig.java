package com.goldencargo.config;

import com.goldencargo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return auth.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/breakdowns/**")
                        .hasAnyRole("ADMIN", "MANAGER", "DRIVER", "USER")

                        .requestMatchers("/clients/**",
                                "/client-invoices/**",
                                "/drivers/**",
                                "/driver-reports/**",
                                "/goods/**",
                                "/invoices/**",
                                "/news/**",
                                "/users/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers("/client-orders/**",
                                "/orders/**",
                                "/shipping-documents/**")
                        .hasAnyRole("ADMIN", "MANAGER", "LOGISTIC")

                        .requestMatchers("/damages/**",
                                "/incidents/**")
                        .hasAnyRole("ADMIN", "DRIVER", "USER")

                        .requestMatchers("/locations/**",
                                "/routes/**",
                                "/transports/**",
                                "/transport-orders/**")
                        .hasAnyRole("ADMIN", "LOGISTIC")

                        .requestMatchers("/service-schedules/**",
                                "/technical-inspections/**",
                                "/vehicles/**",
                                "/vehicle-repairs/**",
                                "/vehicle-types/**")
                        .hasAnyRole("ADMIN", "USER")

                        .requestMatchers("/reports/**",
                                "/email/**")
                        .permitAll()

                        .requestMatchers(
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/users/forgot-password",
                                "/users/reset-password**",
                                "/email/send-pdf",
                                "/save-to-dropbox"
                        ).permitAll()

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/logout-success")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/access-denied");
                        })
                );
        return http.build();
    }

}

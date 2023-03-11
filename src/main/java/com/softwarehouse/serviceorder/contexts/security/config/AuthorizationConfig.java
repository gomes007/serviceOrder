package com.softwarehouse.serviceorder.contexts.security.config;

import com.softwarehouse.serviceorder.contexts.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorizationConfig {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Bean
    public SecurityFilterChain configure(HttpSecurity builder) throws Exception {
        return builder.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/authenticate").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                .antMatchers(HttpMethod.GET, "/docs").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui*/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v3/api-docs*/**").permitAll()
                .anyRequest().authenticated().and()
                .addFilter(new JwtTokenFilter(this.authenticationManager(), this.userService))
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    private AuthenticationManager authenticationManager() {
        return this.authenticationManagerBuilder.getObject();
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
        };
    }
}

package com.rai69.Foro_Hub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
               .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authorizeHttpRequests(auth -> auth
                       // Endpoints públicos
                       .requestMatchers("/auth/**").permitAll()
                       .requestMatchers("/perfiles").permitAll()
                       .requestMatchers(HttpMethod.GET, "/topicos").permitAll()
                       .requestMatchers(HttpMethod.GET, "/topicos/**").permitAll()
                       .requestMatchers(HttpMethod.GET, "/cursos").permitAll()
                       .requestMatchers(HttpMethod.GET, "/cursos/**").permitAll()
                       .requestMatchers(HttpMethod.GET, "/usuarios/**").permitAll()
                       .requestMatchers(HttpMethod.DELETE, "/topicos/**").hasRole("ADMIN")
                       .requestMatchers(HttpMethod.DELETE, "/cursos/**").hasRole("ADMIN")
                       .requestMatchers(HttpMethod.POST, "/cursos").hasAnyRole("ADMIN", "MODERADOR")
                       .requestMatchers(HttpMethod.POST, "/topicos").authenticated()
                       .requestMatchers(HttpMethod.PUT, "/topicos/**").authenticated()
                       .anyRequest().authenticated()
               )
               .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

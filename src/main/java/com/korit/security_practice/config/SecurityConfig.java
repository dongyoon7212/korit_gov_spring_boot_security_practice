package com.korit.security_practice.config;

import com.korit.security_practice.security.filter.JwtAuthenticationFilter;
import com.korit.security_practice.security.handler.OAuth2SuccessHandler;
import com.korit.security_practice.service.OAuth2PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private OAuth2PrincipalService oAuth2PrincipalService;

    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        corsConfiguration.addAllowedOriginPattern(CorsConfiguration.ALL);
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        http.formLogin(formLogin -> formLogin.disable());
        http.httpBasic(httpBasic -> httpBasic.disable());
        http.logout(logout -> logout.disable());

        http.sessionManagement(Session -> Session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/auth/**").permitAll();
            auth.anyRequest().authenticated();
        });

        http.oauth2Login(oauth2 ->
                oauth2.userInfoEndpoint(userInfo ->
                        userInfo.userService(oAuth2PrincipalService))
                        .successHandler(oAuth2SuccessHandler)
                );

        return http.build();
    }
}












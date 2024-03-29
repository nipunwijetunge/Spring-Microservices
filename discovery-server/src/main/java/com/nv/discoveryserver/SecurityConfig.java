package com.nv.discoveryserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Value("${eureka.username}")
    String username = "eureka";

    @Value("${eureka.password}")
    String password = "password";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) -> authz
                        .anyRequest().hasRole("USER"))
                .httpBasic(withDefaults());
        http.csrf((csrf) -> csrf.ignoringRequestMatchers("/eureka/**"));
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((requests) -> requests.requestMatchers("/eureka/**").hasRole("USER"))
//                .httpBasic(withDefaults());
//        return http.build();
//    }
//
    @Bean
    public UserDetailsService users() {
        // The builder will ensure the passwords are encoded before saving in memory
        //User.UserBuilder users = User;
        UserDetails user = User.builder()
                .username(username)
                .password(encoder().encode(password))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}

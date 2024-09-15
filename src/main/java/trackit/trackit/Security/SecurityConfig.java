package trackit.trackit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // return httpSecurity.formLogin(httpForm -> {
        // httpForm.loginPage("/login").permitAll();
        // })
        // .authorizeHttpRequests(registry -> {
        // registry.requestMatchers("/api/v1/auth/**").permitAll();
        // registry.anyRequest().authenticated();
        // })
        // .build();

        // Disable CSRF for simplicity in this example (enable it in production with
        // proper token handling)
        httpSecurity
                .csrf(customizer -> customizer.disable());

                // Enable form login
         httpSecurity
         .formLogin(formLogin -> formLogin
             .loginPage("/login") // Specify your custom login page
             .permitAll()          // Allow access to everyone
         );

        // Authorize requests
        httpSecurity.authorizeHttpRequests(customizer -> {
            // Permit access to /api/v1/auth/login without authentication
            customizer.requestMatchers(HttpMethod.GET, "/api/v1/auth/login").permitAll();
             // Allow access to any authentication-related APIs
             customizer.requestMatchers("/api/v1/auth/login").permitAll();;
            // Any other request must be authenticated
            customizer.anyRequest().authenticated();
        });

        return httpSecurity.build();

    }

}

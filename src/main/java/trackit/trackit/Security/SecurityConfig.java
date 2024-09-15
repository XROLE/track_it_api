package trackit.trackit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // // Authorize requests
        // httpSecurity.authorizeHttpRequests(customizer -> {
        //     // Permit access to /api/v1/auth/login without authentication
        //     customizer.requestMatchers(HttpMethod.POST, "/api/v1/auth/signup").permitAll();
        //     // Allow access to any authentication-related APIs
        //     customizer.requestMatchers("/api/v1/auth/signup").permitAll();
        //     ;
        //     // Any other request must be authenticated
        //     customizer.anyRequest().authenticated();
        // }).csrf(cs -> cs.disable());

        // httpSecurity
        // .formLogin(formLogin -> formLogin
        //         .loginPage("/login") // Specify your custom login page
        //         .permitAll() // Allow access to everyone
        // );


    httpSecurity.csrf(customizer -> customizer.disable());
    httpSecurity.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    httpSecurity.authorizeHttpRequests(customizer -> {
      customizer.requestMatchers("/**", "/**").permitAll();
    //   customizer.requestMatchers(HttpMethod.POST, "/api/v1/auth/**", "/api/v1/auth/signup").permitAll();
      customizer.anyRequest().authenticated();
    });

        return httpSecurity.build();

    }

}

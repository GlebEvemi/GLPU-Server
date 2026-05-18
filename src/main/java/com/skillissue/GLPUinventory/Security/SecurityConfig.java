package com.skillissue.GLPUinventory.Security;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration for the application.
 * Configures HTTP Basic authentication and role-based access control.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig{

    /**
     * Configures the security filter chain for HTTP requests.
     * - Disables CSRF for API endpoints (stateless authentication)
     * - Requires ADMIN role for all /api/** endpoints
     * - Requires authentication for all other requests
     * - Uses HTTP Basic authentication
     * 
     * @param http HttpSecurity to configure
     * @return configured SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF protection for REST API (stateless, uses HTTP Basic auth)
            .csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests((authorize) -> authorize
            // Require ADMIN role for all API endpoints
            .requestMatchers("/api/**").hasRole("ADMIN")
            // All other requests require authentication
            .anyRequest().authenticated())
            //.formLogin(Customizer.withDefaults())
	    // Enable HTTP Basic authentication (username:password in header)
	    .httpBasic(Customizer.withDefaults());
        return http.build();
    }



    
}

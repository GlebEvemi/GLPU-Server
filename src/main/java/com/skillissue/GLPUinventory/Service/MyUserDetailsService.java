package com.skillissue.GLPUinventory.Service;


import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skillissue.GLPUinventory.Entity.User;
import com.skillissue.GLPUinventory.Repository.UserRepository;

/**
 * Custom UserDetailsService implementation for Spring Security.
 * Loads user details from the database for authentication.
 * Responsible for providing user credentials and roles to the authentication system.
 */
@Service
public class MyUserDetailsService implements UserDetailsService{
    
    private final UserRepository userRepository;

    /**
     * Constructor with dependency injection of UserRepository.
     * @param userRepository the data access object for User entities
     */
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads user details by username from the database.
     * Called by Spring Security during authentication process.
     * @param username the user's login name
     * @return UserDetails object with credentials and authorities
     * @throws UsernameNotFoundException if user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from database; throw exception if not found
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        // Build Spring Security UserDetails with user's credentials and role
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPass())
                .roles(user.getRole())
                .build();
    }

    // @Bean
	// public AuthenticationManager authenticationManager(
	// 		UserDetailsService userDetailsService,
	// 		PasswordEncoder passwordEncoder) {
	// 	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
	// 	authenticationProvider.setPasswordEncoder(passwordEncoder);

	// 	return new ProviderManager(authenticationProvider);
	// }


    /**
     * Provides the PasswordEncoder bean for encoding/decoding passwords.
     * Uses Spring Security's delegating password encoder for flexibility.
     * @return PasswordEncoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


     /**
      * Provides the AuthenticationProvider bean for authenticating users.
      * Combines UserDetailsService and PasswordEncoder for credential validation.
      * @param userDetailsService the UserDetailsService to load user information
      * @param passwordEncoder the PasswordEncoder for password validation
      * @return AuthenticationProvider bean
      */
     @Bean
     public AuthenticationProvider authProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
         authProvider.setPasswordEncoder(passwordEncoder);

         return authProvider;
        
     }
}

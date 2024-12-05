package com.bl.fundoo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JWTFilter filter;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception
	{
		return security.csrf(csrf -> csrf.disable())
						.authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/users/adduser","/api/v1/users/login").permitAll().anyRequest().authenticated())
						.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
						.httpBasic(Customizer.withDefaults())
						.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
						.authenticationProvider(authenticationProvider())
						.build();
				
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider provider =new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(myUserDetailsService);
		return provider;
	}
	@Bean 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
	{
		return configuration.getAuthenticationManager();
	}
}

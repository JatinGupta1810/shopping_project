package com.shopping.project.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class SecurityConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private OncePerRequestFilter customFilter;

	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain customeFilterChain(@Autowired HttpSecurity http) throws Exception {

		LOGGER.info("Inside SecurityConfig customeFilterChain()");
		http.authorizeHttpRequests((req) -> req.requestMatchers("/login/welcome", "/login/sign-in").permitAll()
				.anyRequest().authenticated()).csrf().disable().httpBasic();
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	public void customAuthenticationBuilder(@Autowired AuthenticationManagerBuilder auth) throws Exception {

		LOGGER.info("customAuthenticationBuilder method");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

}

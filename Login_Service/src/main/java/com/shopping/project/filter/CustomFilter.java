package com.shopping.project.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shopping.project.utility.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomFilter extends OncePerRequestFilter {

	private final static Logger LOG = LoggerFactory.getLogger(CustomFilter.class);

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		LOG.info("CustomFilter.doFilterInternal");
		LOG.info("LoggedInUser: {}", request.getHeader("LOGGED_IN_USER"));
		String token = request.getHeader("Authorization");
		LOG.info("Token: " + token);
		UserDetails userDetails = null;

		if (token != null && token.startsWith("Bearer ")) {

			token = token.substring(7);
			LOG.info("Token: " + token);

			try {
				String username = jwtTokenUtil.extractUsername(token);
				userDetails = userDetailsService.loadUserByUsername(username);
			} catch (Exception e) {
				LOG.error("Error occurred in CustomFilter.doFilterInternal: " + e.getMessage());
				throw e;
			}

			if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				userAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(userAuthentication);
			}

		}

		filterChain.doFilter(request, response);

	}

}

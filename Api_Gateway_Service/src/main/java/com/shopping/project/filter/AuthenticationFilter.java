package com.shopping.project.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import com.shopping.project.config.RouteValidator;
import com.shopping.project.utility.JwtTokenUtil;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	private static final String BEARER = "Bearer ";
	private static final String LOGGED_IN_USER = "LOGGED_IN_USER";
	private final static Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

	@Autowired
	private RouteValidator routeValidator;

	@Autowired
	private JwtTokenUtil tokenUtil;

	public AuthenticationFilter() {
		super(Config.class);
	}

	public final static class Config {

	}

	@Override
	public GatewayFilter apply(Config config) {

		return ((exchange, chain) -> {
			String headerValue = "Checking";
			LOG.info("Request: {}", exchange.getRequest());
			ServerHttpRequest request = null;

			if (routeValidator.isSecured.test(exchange.getRequest())) {
				LOG.info("Validation required");
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("Authorization header is not present");
				}

				String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if (token != null && token.startsWith(BEARER)) {
					token = token.substring(7);
					if (!tokenUtil.validateToken(token)) {
						throw new RuntimeException("Invalid Token");
					}
					String username = tokenUtil.extractUsername(token);
					headerValue = username;
				}

			}
			request = exchange.getRequest().mutate().header(LOGGED_IN_USER, headerValue).build();
			return chain.filter(exchange.mutate().request(request).build());
		});
	}

}

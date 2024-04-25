package com.shopping.project.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.shopping.project.entity.UserInfo;
import com.shopping.project.repo.UserInfoRepo;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserInfoRepo userInfoRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserInfo userInfo = userInfoRepo.findByUsername(username);
		LOGGER.info("User: {}", userInfo);

		if (userInfo == null) {
			throw new UsernameNotFoundException("Invalid username");
		}

		CustomUser customUser = new CustomUser(userInfo);
		return customUser;
	}

}

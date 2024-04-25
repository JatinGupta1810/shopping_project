package com.shopping.project.service;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.shopping.project.constant.Constants;
import com.shopping.project.dto.UserDataDto;
import com.shopping.project.dto.UserInfoDto;
import com.shopping.project.dto.UserProfileDto;
import com.shopping.project.entity.UserInfo;
import com.shopping.project.repo.UserInfoRepo;
import com.shopping.project.utility.JwtTokenUtil;

@Service
public class MyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyService.class);

	@Autowired
	private UserInfoRepo userInfoRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenUtil tokenUtil;

	@Autowired
	private RestTemplate restTemplate;

	@Transactional
	public String signIn(UserDataDto userDataDto) {

		UserInfoDto userInfoDto = userDataDto.getInfoDto();
		LOGGER.info("UserInfoDto: " + userInfoDto);
		UserInfo userInfo = new UserInfo();
		BeanUtils.copyProperties(userInfoDto, userInfo);
		userInfo.setCreatedDate(new Date(new java.util.Date().getTime()));
		LOGGER.info("UserInfo: " + userInfo);
		String encodedPassword = passwordEncoder.encode(userInfo.getPassword());
		LOGGER.info("Password: {}", encodedPassword);
		userInfo.setPassword(encodedPassword);
		userInfoRepo.save(userInfo);

		UserProfileDto profileDto = userDataDto.getProfileDto();
		String url = "http://" + Constants.PROFILE_SERVICE + "/profile/add-data";
		String response = restTemplate.postForObject(url, profileDto, String.class);
		LOGGER.info("RESPONSE: {}", response);

		String token = tokenUtil.generateToken(userInfo.getUsername());

		return token;
	}

	public String logIn() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		LOGGER.info("UserName: " + username);
		return tokenUtil.generateToken(username);
	}
}

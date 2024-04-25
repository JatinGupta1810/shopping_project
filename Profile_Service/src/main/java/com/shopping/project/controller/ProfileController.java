package com.shopping.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.project.dto.UserProfileDto;
import com.shopping.project.service.ProfileService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	private ProfileService profileService;

	@PostMapping("/add-data")
	public ResponseEntity<String> addProfileData(@RequestBody UserProfileDto userProfileDto) {
		LOGGER.info("Inside addProfileData");
		String result = profileService.addProfileData(userProfileDto);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@PostMapping("/get-data")
	public ResponseEntity<UserProfileDto> getProfileData(HttpServletRequest httpServletRequest) {
		LOGGER.info("Inside getProfileData");
		String username = httpServletRequest.getHeader("LOGGED_IN_USER");
		LOGGER.info("Username: " + username);
		UserProfileDto userProfileDto = profileService.getProfileData(username);
		return new ResponseEntity<>(userProfileDto, HttpStatus.OK);
	}

}

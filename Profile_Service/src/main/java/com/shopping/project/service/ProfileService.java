package com.shopping.project.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.project.dto.UserProfileDto;
import com.shopping.project.entity.UserProfile;
import com.shopping.project.repo.ProfileRepo;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepo profileRepo;

	public String addProfileData(UserProfileDto userProfileDto) {

		UserProfile userProfile = new UserProfile();
		BeanUtils.copyProperties(userProfileDto, userProfile);
		profileRepo.save(userProfile);
		return "Profile Data saved successfully";
	}

	public UserProfileDto getProfileData(String username) {

		UserProfile userProfile = profileRepo.findByUsername(username);
		UserProfileDto userProfileDto = new UserProfileDto();
		BeanUtils.copyProperties(userProfile, userProfileDto);
		return userProfileDto;
	}

}

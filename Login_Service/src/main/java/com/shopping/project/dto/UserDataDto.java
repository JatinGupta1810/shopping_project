package com.shopping.project.dto;

public class UserDataDto {

	private UserInfoDto infoDto;
	private UserProfileDto profileDto;

	public UserInfoDto getInfoDto() {
		return infoDto;
	}

	public void setInfoDto(UserInfoDto infoDto) {
		this.infoDto = infoDto;
	}

	public UserProfileDto getProfileDto() {
		return profileDto;
	}

	public void setProfileDto(UserProfileDto profileDto) {
		this.profileDto = profileDto;
	}

	@Override
	public String toString() {
		return "UserDataDto [infoDto=" + infoDto + ", profileDto=" + profileDto + "]";
	}

}

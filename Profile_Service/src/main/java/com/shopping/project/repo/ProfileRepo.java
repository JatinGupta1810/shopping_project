package com.shopping.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.project.entity.UserProfile;

public interface ProfileRepo extends JpaRepository<UserProfile, Integer> {

	public UserProfile findByUsername(String username);
}

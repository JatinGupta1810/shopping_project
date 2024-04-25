package com.shopping.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.project.entity.UserInfo;

public interface UserInfoRepo extends JpaRepository<UserInfo, Integer> {

	UserInfo findByUsername(String UserName);
}

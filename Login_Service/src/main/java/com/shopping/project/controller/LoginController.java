package com.shopping.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.project.dto.UserDataDto;
import com.shopping.project.service.MyService;

@RestController
@RequestMapping("/login")
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private MyService myService;

	@Value("${server.port}")
	private String servicePort;

	@GetMapping("/welcome")
	public ResponseEntity<?> welcome() {
		LOGGER.info("Inside Login Service");
		Map<String, Object> map = new HashMap<>();
		map.put("Port", servicePort);
		map.put("Welcome", "Welcome to shopping app");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@PostMapping("/sign-in")
	public ResponseEntity<?> signIn(@RequestBody UserDataDto userDataDto) {
		LOGGER.info("LoginController.saveCredentials()");
		String token = myService.signIn(userDataDto);
		Map<String, Object> map = new HashMap<>();
		map.put("Jwt", token);
		map.put("Port", servicePort);
		map.put("Response", "User credentials successfully");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@GetMapping("/log-in")
	public ResponseEntity<?> logIn() {
		LOGGER.info("Inside logIn method");
		String token = myService.logIn();
		Map<String, Object> map = new HashMap<>();
		map.put("Jwt", token);
		map.put("Port", servicePort);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@GetMapping("/private")
	public ResponseEntity<?> privateMethod() {
		LOGGER.info("Inside private  method");
		Map<String, Object> map = new HashMap<>();
		map.put("Response", "This is private method");
		map.put("Port", servicePort);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

}

package com.example.jwt.controller;

import com.example.jwt.model.JwtRequest;



import com.example.jwt.model.JwtResponse;
import com.example.jwt.service.UserService;
import com.example.jwt.utility.JwtUtility;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {
	
	@Autowired
	private JwtUtility jwtUtility;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/")
	public String Home() {
		return "Welcome to JWT Generation";
		
	}
	

	@PostMapping("/authenticate")
	public JwtResponse  authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
	} catch (BadCredentialsException e) {
			throw  new Exception("Invalid Credentials", e);
		}
		
		
		
		final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUserName());
		final String token = jwtUtility.generateToken(userDetails);
		return new JwtResponse(token);
		
		
		
	}	
	

		
		
		
		
	}
	
       
   

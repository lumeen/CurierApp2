package com.social.controller;

import com.social.dao.CourierRepository;
import com.social.model.AvailabilityRequest;
import com.social.model.CoordsUpdateRequest;
import com.social.model.VerifyNfcRequest;
import com.social.security.JwtAuthenticationRequest;
import com.social.security.JwtAuthenticationResponse;
import com.social.security.JwtTokenUtil;


import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.social.services.UserService;
import com.social.util.CustomErrorType;
import com.social.entities.User;

@RestController
public class AccountController {

	public static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private CourierRepository curierRepository;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@CrossOrigin
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User newUser) {
		if (userService.find(newUser.getUsername()) != null) {
			logger.error("username Already exist " + newUser.getUsername());
			return new ResponseEntity(
					new CustomErrorType("user with username " + newUser.getUsername() + "already exist "),
					HttpStatus.CONFLICT);
		}
		newUser.setRole("USER");
		
		return new ResponseEntity<User>(userService.save(newUser), HttpStatus.CREATED);
	}


	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest) {
		final Authentication authentication = authenticationManager.authenticate(new
			UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest
			.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		final UserDetails userDetails = this.userService.find(authenticationRequest.getUsername());
		 final java.lang.String token = this.jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/loginMob")
	public ResponseEntity<?> loginMobile(JwtAuthenticationRequest authenticationRequest) {
		final Authentication authentication = authenticationManager.authenticate(new
			UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest
			.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		final UserDetails userDetails = this.userService.find(authenticationRequest.getUsername());
		final java.lang.String token = this.jwtTokenUtil.generateToken(userDetails);
		System.out.println(token);
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}

	@CrossOrigin
	@RequestMapping(value = "/verifyNfc", method = RequestMethod.PUT)
	public ResponseEntity<?> verifyNfc(VerifyNfcRequest verifyNfcRequest) {
		return userService.verifyNfc(verifyNfcRequest) ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	@CrossOrigin
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<SimpleResponse> user() {
		return ResponseEntity.ok(new SimpleResponse("bb"));
	}

    @CrossOrigin
    @RequestMapping(value = "/availability", method = RequestMethod.PUT)
    public ResponseEntity<?> setAvailability(AvailabilityRequest availabilityRequest) {

userService.setAvailability(availabilityRequest);

        return ResponseEntity.ok("");
    }

	@CrossOrigin
	@RequestMapping(value = "/courier/updateCoords", method = RequestMethod.POST
	)
	public void addParcelToCourier ( CoordsUpdateRequest coordsUpdateRequest) {
userService.updateCourierCoords(coordsUpdateRequest);
	}

	@CrossOrigin
	@RequestMapping(value = "/getSaldo", method = RequestMethod.POST
	)
	public String getSaldo (String userName) {
		return userService.getSaldo(userName);
	}

	@CrossOrigin
	@RequestMapping(value = "/setCourierReady", method = RequestMethod.PUT
	)
	public void setCourierReady (String userName) {
		 userService.setCourierReady(userName);
	}
}

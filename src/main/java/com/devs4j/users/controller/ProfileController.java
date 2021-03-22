package com.devs4j.users.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.devs4j.users.entities.Profile;
import com.devs4j.users.service.ProfileService;

@Controller
@RequestMapping("/users/{userId}/profiles")
public class ProfileController {
	
	
	private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	private ProfileService profileService;
	
	@PostMapping
	public ResponseEntity<Profile> createProfile(@PathVariable("userId") Integer userId, @RequestBody Profile profile){
		log.info("userId {}",userId);
		return new ResponseEntity<Profile>(this.profileService.create(profile, userId), HttpStatus.OK);
	}
	
	@GetMapping("/{profileId}")
	public ResponseEntity<Profile> getById(@PathVariable("userId") Integer userId, @PathVariable("profileId") Integer profileId){
		log.info("userId {}",userId);
		return new ResponseEntity<Profile>(this.profileService.getByUserIdAndProfileId(userId, profileId), HttpStatus.OK);
	}
}

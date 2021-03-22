package com.devs4j.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.devs4j.users.entities.Address;
import com.devs4j.users.service.AddressService;

@Controller
@RequestMapping("/users/{userId}/profiles/{profileId}/addresses")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@GetMapping
	public ResponseEntity<List<Address>> findAddressByProfileAndUserId(@PathVariable("userId") Integer userId, @PathVariable("profileId") Integer profileId){
		return new ResponseEntity<List<Address>>(this.addressService.findAddressByProfileAndUserId(userId, profileId), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Address> create(
			@RequestBody Address address, 
			@PathVariable("userId") Integer userId, 
			@PathVariable("profileId") Integer profileId){
		return new ResponseEntity<Address>(this.addressService.create(address, userId, profileId), HttpStatus.CREATED);
	}

}

package com.devs4j.users.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.devs4j.users.entities.Address;
import com.devs4j.users.entities.Profile;
import com.devs4j.users.repository.AddressRepository;
import com.devs4j.users.repository.ProfileRepository;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ProfileRepository profileRespository;

	public List<Address> findAddressByProfileAndUserId(Integer userId, Integer profileId) {
		return this.addressRepository.findProfileId(profileId, userId);
	}
	
	public Address create(Address address, Integer userId, Integer profileId) {
		Optional<Profile> result = this.profileRespository.findByUserIdAndProfileId(userId, profileId);
		if(result.isPresent()) {
			address.setProfile(result.get());
			return this.addressRepository.save(address);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("profile %d and user %d not found", profileId, userId));
		}
	}
}

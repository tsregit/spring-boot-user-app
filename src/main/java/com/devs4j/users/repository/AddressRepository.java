package com.devs4j.users.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.devs4j.users.entities.Address;

public interface AddressRepository extends CrudRepository<Address, Integer>{
	
	@Query("SELECT a FROM Address a WHERE a.profile.user.id = ?1 AND a.profile.id = ?2")
	List<Address> findProfileId(Integer ProfileId, Integer userId);
}

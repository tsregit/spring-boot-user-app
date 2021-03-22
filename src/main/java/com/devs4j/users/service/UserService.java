package com.devs4j.users.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devs4j.users.entities.User;
import com.devs4j.users.repository.UserRepository;

@Service
public class UserService {
	
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	
	@Autowired
	private UserRepository userRepository;
	
	public Page<User> getUser(int page, int size){
		return this.userRepository.findAll(PageRequest.of(page, size));
	}
	
	public List<User> getAll(){
		return this.userRepository.findAll();
	}
	
	public User getUserById(Integer userId) {
		return this.userRepository.findById(userId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User id %d not found", userId)));
	}
	
	@CacheEvict("users")
	public void deleteUserByUsername(String username) {
		User userFound = getUserByUsername(username);
		this.userRepository.delete(userFound);
	}
	
	/**
	 * No es buena practica agregar cache a un password ya que puede cambiar. Es solo de ejemplo
	 * @param username
	 * @return
	 */
	@Cacheable("users")
	public User getUserByUsername(String username) {
		log.info("getting user by username: {}", username);
		return this.userRepository.findByUsername(username).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Username %s not found", username)));
	}
	
	public User getUserByUsernameAndPassword(String username, String password) {
		return this.userRepository.findByUsernameAndPassword(username, password).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Username %s not found", username)));
	}
	
	public  Page<String> getUsernames(int page, int size) {
		return this.userRepository.findUsernames(PageRequest.of(page, size));
	}
}

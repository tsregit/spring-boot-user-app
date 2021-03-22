package com.devs4j.users;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devs4j.users.entities.Role;
import com.devs4j.users.entities.User;
import com.devs4j.users.entities.UserInRole;
import com.devs4j.users.repository.RoleRepository;
import com.devs4j.users.repository.UserInRoleRepository;
import com.devs4j.users.repository.UserRepository;
import com.github.javafaker.Faker;

@SpringBootApplication
public class UsersAppApplication implements ApplicationRunner{
	
	@Autowired
	private Faker faker;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserInRoleRepository userInRoleRepository;
	
	
	private static final Logger log = LoggerFactory.getLogger(UsersAppApplication.class);

	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		Role roles[] = {new Role("ADMIN"), new Role("SUPPORT"), new Role("USER")};
		
		for(Role role: roles) {
			this.roleRepository.save(role);
		}
		
		for(int i = 0; i <= 10; i++) {
			User user = new User();
			user.setUsername(faker.name().username());
			user.setPassword(faker.dragonBall().character());
			User userCreated = this.userRepository.save(user);
			UserInRole userInRole = new UserInRole(userCreated, roles[new Random().nextInt(3)]);
			log.info("User created username {} password {} role {}", userCreated.getUsername(), userCreated.getPassword(), userInRole.getRole().getName());
			this.userInRoleRepository.save(userInRole);
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(UsersAppApplication.class, args);
	}

}

package com.devs4j.users.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devs4j.users.entities.Role;
import com.devs4j.users.entities.User;
import com.devs4j.users.service.RoleService;

@RequestMapping("/roles")
@RestController
public class RoleController {
	
	
	private static final Logger log = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;
	
	@GetMapping
	public ResponseEntity<List<Role>> getRoles(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("Name {}", authentication.getName());
		log.info("Principal {}", authentication.getPrincipal());
		log.info("Credentials {}", authentication.getCredentials());
		log.info("Roles {}", authentication.getAuthorities().toString());
		return new ResponseEntity<List<Role>>(this.roleService.getRoles(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Role> createRole(@RequestBody Role role){
		return new ResponseEntity<Role>(this.roleService.createRole(role), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{roleId}")
	public ResponseEntity<Role> updatedRole(@PathVariable("roleId") Integer roleId, @RequestBody Role role){
		return new ResponseEntity<Role>(this.roleService.updatedRole(role, roleId), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{roleId}")
	public ResponseEntity<Void> deleteRole(@PathVariable("roleId") Integer roleId){
		this.roleService.detelRole(roleId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/{roleName}/users")
	public ResponseEntity<List<User>> getUsersByRole(@PathVariable("roleName") String roleName){
		return new ResponseEntity<List<User>>(this.roleService.getUserByRole(roleName), HttpStatus.OK);
	}

}

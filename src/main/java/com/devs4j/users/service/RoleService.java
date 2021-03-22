package com.devs4j.users.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.devs4j.users.entities.Role;
import com.devs4j.users.entities.User;
import com.devs4j.users.repository.RoleRepository;
import com.devs4j.users.repository.UserInRoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserInRoleRepository userInRoleRepository;
	
	@Secured({"ROLE_ADMIN"}) // Anotacion de Spring
	// @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	// @PostAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> getUserByRole(String roleName){
		return this.userInRoleRepository.findUserByRoleName(roleName);
	}
	
	public List<Role> getRoles(){
		return this.roleRepository.findAll();
	}
	
	public Role createRole(Role role) {
		return this.roleRepository.save(role);
	}
	
	public Role updatedRole(Role role, Integer roleId) {
		Optional<Role> roleFound = this.roleRepository.findById(roleId);
		if(roleFound.isPresent()) {
			return this.roleRepository.save(role);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role id %d doesn`t exists", roleId));
		}
		
	}
	
	public void detelRole(Integer roleId) {
		Optional<Role> role = this.roleRepository.findById(roleId);
		if(role.isPresent()){
			this.roleRepository.delete(role.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role id %d doesn`t exists", roleId));
		}
	}
}

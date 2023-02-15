package com.tigerslab.tigererp.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigerslab.tigererp.model.Role;
import com.tigerslab.tigererp.repository.user.RoleRepository;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Role findByRole(String role) {
		return roleRepository.findByRole(role);
	}

}

package com.tigerslab.tigererp.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.Role;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	public Role findByRole(String role);
	
	public Page<Role> findByRole(String role, Pageable pageable);

}
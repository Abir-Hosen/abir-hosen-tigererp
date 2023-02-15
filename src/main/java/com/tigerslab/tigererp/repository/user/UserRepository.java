package com.tigerslab.tigererp.repository.user;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.Role;
import com.tigerslab.tigererp.model.User;
import com.tigerslab.tigererp.model.UserExceptPassword;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;


@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("select u from User u where u.firstName like %?1% or u.id like %?1%")
	Page<User> findAllBySortAndOrder(String firstName, Pageable pageable);
	
	public Optional<User> findById(long userId);
	
	public User findByEmail(String userEmail);
	
	@Query(value="UPDATE User u SET u.password = ?2 WHERE u.id = ?1", nativeQuery = true)
	public User updatePasswordOnly(Long id, String password);
	
	@Query("select u from User u INNER JOIN u.empRoles e INNER JOIN u.roles r")
	Page<UserExceptPassword> findByRoles(Set<Role> roles, Pageable pageable);
	
	@Query("select u from User u INNER JOIN u.employee e2 INNER JOIN u.empRoles e INNER JOIN u.roles r")
	Page<UserExceptPassword> findByRoles(Set<Role> roles, boolean onlyEmployee, Pageable pageable);
	
}

package com.tigerslab.tigererp.service.user;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.Role;
import com.tigerslab.tigererp.model.User;
import com.tigerslab.tigererp.model.UserExceptPassword;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;

public interface UserService {
	
	public Optional<User> findById(long userId);
	
	public User findUserByEmail(String userEmail);
	
	public User saveUser(User user, String role);
	
	public User save(User user);
	
	public User updateUserPasswordOnly(Long id, String password);
	
	public void saveUserWithEmployeeRole(User user);
	
	Page<UserExceptPassword> findByRoles(String roles, RequestParameter requestParameter);
	
	Page<UserExceptPassword> findByRoles(String roles, boolean onlyEmployee, RequestParameter requestParameter);

	public void deleteUser(User user);

}

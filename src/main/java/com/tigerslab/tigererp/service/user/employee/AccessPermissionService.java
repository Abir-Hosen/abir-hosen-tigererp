package com.tigerslab.tigererp.service.user.employee;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;

public interface AccessPermissionService {
	
	public Optional<AccessPermission> findById(Long id);
	
	public AccessPermission save(AccessPermission accessPermission);

	public void deleteById(Long id);
	
	public void delete(AccessPermission accessPermission);

	public Optional<AccessPermission> findByRoleId(Long roleId);

	public Optional<AccessPermission> findByIdAndEmployeeRoleId(Long id, Long roleId);
	
	public Optional<AccessPermission> findByEmployeeRoleUserEmail(String email);
	
	public Optional<AccessPermission> getLoggegUserAccessPermission(HttpSession session);

}
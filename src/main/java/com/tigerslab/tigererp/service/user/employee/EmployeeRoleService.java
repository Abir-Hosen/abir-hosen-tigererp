package com.tigerslab.tigererp.service.user.employee;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;

public interface EmployeeRoleService {
	
	public Page<EmployeeRole> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public Optional<EmployeeRole> findById(Long id);
	
	public EmployeeRole findByRoleName(String roleName);
	
	public EmployeeRole save(EmployeeRole employeeRole);

	public void deleteById(Long id);
}
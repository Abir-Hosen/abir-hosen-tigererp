package com.tigerslab.tigererp.repository.user.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import com.tigerslab.tigererp.model.Role;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;

@Repository("employeeRoleRepository")
public interface EmployeeRoleRepository extends PagingAndSortingRepository<EmployeeRole, Long> {
	
	@Query("select t from EmployeeRole t where t.roleName like %?1% or t.id like %?1%")
	Page<EmployeeRole> findAllBySortAndOrder(String roleName, Pageable pageable);
	
	public EmployeeRole findByRoleName(String roleName);
}
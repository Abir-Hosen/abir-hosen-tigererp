package com.tigerslab.tigererp.repository.user.employee;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;

@Repository("accessPermissionRepository")
public interface AccessPermissionRepository extends PagingAndSortingRepository<AccessPermission, Long> {
	
	@Query("select t from EmployeeRole t where t.roleName like %?1% or t.id like %?1%")
	Page<EmployeeRole> findAllBySortAndOrder(String roleName, Pageable pageable);
	
	Optional<AccessPermission> findById(Long id);

	Optional<AccessPermission> findByEmployeeRoleId(Long roleId);
	
	Optional<AccessPermission> findByIdAndEmployeeRoleId(Long id, Long roleId);
	
//	@Query("select u from User u INNER JOIN u.empRoles e INNER JOIN u.roles r")
//	
//	@Query("select a from AccessPermission a INNER JOIN a.employeeRole e INNER JOIN e. e where a.userEmail")
//	Optional<AccessPermission> findByEmployeeRoleUserEmail(String email);

}
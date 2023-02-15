package com.tigerslab.tigererp.repository.user.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.user.employee.Employee;

@Repository("employeeRepository")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	@Query("select e from Employee e where e.userName like %?1% or e.id like %?1%")
	Page<Employee> findAllBySortAndOrder(String userName, Pageable pageable);
}

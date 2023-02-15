package com.tigerslab.tigererp.service.user.employee;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.user.employee.Employee;

public interface EmployeeService {
	
	public Page<Employee> findAllBySortAndOrder(RequestParameter requestParameter);
	public Optional<Employee> findById(Long id);
	public Employee save(Employee employee);
	public void deleteById(Long id);
}

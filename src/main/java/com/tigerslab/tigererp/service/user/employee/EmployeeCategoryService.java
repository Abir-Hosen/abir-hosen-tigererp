package com.tigerslab.tigererp.service.user.employee;

import java.util.Optional;
import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.user.employee.EmployeeCategory;

public interface EmployeeCategoryService {
	
	public Page<EmployeeCategory> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public Optional<EmployeeCategory> findById(Long id);
	
	public EmployeeCategory findByRoleName(String categoryName);
	
	public EmployeeCategory save(EmployeeCategory employeeCategory);

	public void deleteById(Long id);
}

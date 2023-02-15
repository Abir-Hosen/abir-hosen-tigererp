package com.tigerslab.tigererp.service.org.inventory;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.inventory.Units;
import com.tigerslab.tigererp.model.user.employee.Employee;

public interface UnitsService {
	
	public Page<Units> findAllBySortAndOrder(RequestParameter requestParameter);
	public Optional<Units> findById(Long id);
	public Units save(Units unit);
	public void deleteById(Long id);
}

package com.tigerslab.tigererp.service.user.employee;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.user.employee.Employee;
import com.tigerslab.tigererp.repository.user.employee.EmployeeRepository;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	
	@Autowired
	EmployeeRepository employeeRepository;

	private Pageable pagable;
	
	@Override
	public Page<Employee> findAllBySortAndOrder(RequestParameter requestParam) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParam.getPage() >= 1) {
			requestParam.setPage(requestParam.getPage()-1);
		}
		if(requestParam.getOrder().charAt(0) == '-') {
			String sortOrder = requestParam.getOrder().substring(1);
			pagable = PageRequest.of(requestParam.getPage(), requestParam.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParam.getPage(), requestParam.getLimit(), Sort.by(requestParam.getOrder()).ascending());
		}
		return employeeRepository.findAllBySortAndOrder(requestParam.getFilter(), pagable);
	}

	@Override
	public Optional<Employee> findById(Long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public void deleteById(Long id) {
		employeeRepository.deleteById(id);
	}

}

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
import com.tigerslab.tigererp.model.user.employee.EmployeeCategory;
import com.tigerslab.tigererp.repository.user.employee.EmployeeCategoryRepository;

@Service("employeeCategory")
public class EmployeeCategoryServiceImpl implements EmployeeCategoryService{

	Logger logger = LoggerFactory.getLogger(EmployeeCategoryService.class);
	
	@Autowired
	EmployeeCategoryRepository employeeCategoryRepository;

	private Pageable pagable;
	
	@Override
	public Page<EmployeeCategory> findAllBySortAndOrder(RequestParameter requestParam) {
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
		return employeeCategoryRepository.findAllBySortAndOrder(requestParam.getFilter(), pagable);
	}

	@Override
	public Optional<EmployeeCategory> findById(Long id) {
		return employeeCategoryRepository.findById(id);
	}

	@Override
	public EmployeeCategory findByRoleName(String categoryName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeCategory save(EmployeeCategory employeeCategory) {
		return employeeCategoryRepository.save(employeeCategory);
	}

	@Override
	public void deleteById(Long id) {
		employeeCategoryRepository.deleteById(id);
	}

}

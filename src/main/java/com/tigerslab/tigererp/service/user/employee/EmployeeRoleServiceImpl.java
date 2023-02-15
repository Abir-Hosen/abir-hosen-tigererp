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
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;
import com.tigerslab.tigererp.repository.user.employee.EmployeeRoleRepository;

@Service("employeeRole")
public class EmployeeRoleServiceImpl implements EmployeeRoleService {
	
	Logger logger = LoggerFactory.getLogger(EmployeeRoleService.class);
	
	@Autowired
	private EmployeeRoleRepository employeeRoleRepository;
	
	private Pageable pagable;

	@Override
	public Page<EmployeeRole> findAllBySortAndOrder(RequestParameter requestParam) {
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
		return employeeRoleRepository.findAllBySortAndOrder(requestParam.getFilter(), pagable);
	}

	@Override
	public EmployeeRole save(EmployeeRole employeeRole) {
		return employeeRoleRepository.save(employeeRole);
	}
	

	@Override
	public Optional<EmployeeRole> findById(Long id) {
		return employeeRoleRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		employeeRoleRepository.deleteById(id);
	}

	@Override
	public EmployeeRole findByRoleName(String roleName) {
		// TODO Auto-generated method stub
		return employeeRoleRepository.findByRoleName(roleName);
	}
}
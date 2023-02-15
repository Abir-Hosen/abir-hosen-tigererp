package com.tigerslab.tigererp.service.org.inventory;

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
import com.tigerslab.tigererp.model.org.inventory.Units;
import com.tigerslab.tigererp.model.user.employee.Employee;
import com.tigerslab.tigererp.repository.org.inventory.UnitsRepository;
import com.tigerslab.tigererp.repository.user.employee.EmployeeRepository;

@Service("unitsService")
public class UnitsServiceImpl implements UnitsService {

	Logger logger = LoggerFactory.getLogger(UnitsService.class);
	
	@Autowired
	UnitsRepository unitsRepository;

	private Pageable pagable;
	
	@Override
	public Page<Units> findAllBySortAndOrder(RequestParameter requestParam) {
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
		return unitsRepository.findAllBySortAndOrder(requestParam.getFilter(), pagable);
	}

	@Override
	public Optional<Units> findById(Long id) {
		return unitsRepository.findById(id);
	}

	@Override
	public Units save(Units unit) {
		return unitsRepository.save(unit);
	}

	@Override
	public void deleteById(Long id) {
		unitsRepository.deleteById(id);
	}
}

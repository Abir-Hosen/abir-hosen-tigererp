package com.tigerslab.tigererp.controller.user.employee;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.model.user.employee.Employee;
import com.tigerslab.tigererp.service.user.employee.EmployeeService;

@RestController
@RequestMapping("/api/employeeApi")
public class EmployeeApiController {
	
	@Autowired
	EmployeeService employeeService;
	@Autowired
	AccessPermission accessPermission;

	private Logger logger = LoggerFactory.getLogger(AccessPermissionController.class);

	@GetMapping
	public ResponseEntity<Page<Employee>> countryList(RequestParameter requestParam, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadEmployee()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET method");
		Page<Employee> employeeList = (Page<Employee>) employeeService.findAllBySortAndOrder(requestParam);
		return ResponseEntity.ok(employeeList);
	}
	

	@PostMapping
	public ResponseEntity<Employee> create(@RequestBody @Valid Employee employee, BindingResult bindingResult, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getCreateEmployee()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering POST Method");
		logger.info("Employee Info: "+employee);
		StringBuilder serverSideErrors = new StringBuilder("");
		if(bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error : errorList) {
				serverSideErrors.append(", ").append(error.getDefaultMessage());
			}
			logger.error("Form has errors: "+serverSideErrors);
			return ResponseEntity.badRequest().build();
		}
		else {
			Long employeeId = employee.getId();
//			Optional<Employee> checkEmployee = (Optional<Employee>) employeeService.findById(employeeId);
			if(employeeId>0) {
				logger.info("Id "+employeeId+" is existed. it will update here");
//				companyProfileService.(checkCompany.get());
				update(employee, employeeId, bindingResult, session);
				return ResponseEntity.ok(employee);
			}else {
				employeeService.save(employee);
				return ResponseEntity.ok(employee);
			}
			
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Employee> update(@RequestBody @Valid Employee employee, @PathVariable Long id, BindingResult bindingResult, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateEmployee()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering PUT Method");
		logger.info("Employee Info: "+employee);
		StringBuilder serverSideErrors = new StringBuilder("");
		if(bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error : errorList) {
				serverSideErrors.append(", ").append(error.getDefaultMessage());
			}
			logger.error("Form has errors: "+serverSideErrors);
			return ResponseEntity.badRequest().build();
		}else {
			if(!employeeService.findById(id).isPresent()) {
				logger.info("Id "+id+" is not existed");
				return ResponseEntity.badRequest().build();
			}else {
				employeeService.save(employee);
				return ResponseEntity.ok(employee);
			}
		}
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<Employee> delete( @PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteEmployee()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering DELETE Method");
		if(!employeeService.findById(id).isPresent()) {
			logger.info("Id "+id+" is not existed");
			return ResponseEntity.badRequest().build();
		}else {
			employeeService.deleteById(id);
			return ResponseEntity.ok(null);
		}
	}
}

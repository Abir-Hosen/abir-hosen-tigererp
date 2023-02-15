package com.tigerslab.tigererp.controller.user.employee;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
import com.tigerslab.tigererp.model.httphelper.ResponseMetadata;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.model.user.employee.EmployeeCategory;
import com.tigerslab.tigererp.service.user.employee.EmployeeCategoryService;

@Transactional
@RestController
@RequestMapping("/api/employeecategories")
public class EmployeeCategoryController {
	
	@Autowired
	private EmployeeCategoryService employeeCategoryService;
	@Autowired
	AccessPermission accessPermission;
	
	private Logger logger = LoggerFactory.getLogger(EmployeeCategoryController.class);
	
	@GetMapping
	public ResponseEntity<Page<EmployeeCategory>> findAllBySortAndOrder(RequestParameter requestParameter,  HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadEmployeeCategory()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET method");
		Page<EmployeeCategory> page = employeeCategoryService.findAllBySortAndOrder(requestParameter);
		return ResponseEntity.ok(page);
	}
	
	@PostMapping
	public ResponseEntity<EmployeeCategory> create(@RequestBody @Valid EmployeeCategory employeeCategory, BindingResult bindingResult,  HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getCreateEmployeeCategory()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering POST Method");
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
			employeeCategoryService.save(employeeCategory);
			return ResponseEntity.ok(employeeCategory);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<EmployeeCategory> findById(@PathVariable Long id,  HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadEmployeeCategory()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET findById");
		Optional<EmployeeCategory> employeeCategory = employeeCategoryService.findById(id);
		if(!employeeCategory.isPresent()) {
			logger.info("Id "+id+" is not existed");
			ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(employeeCategory.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<EmployeeCategory> update(@PathVariable Long id, @RequestBody @Valid EmployeeCategory employeeCategory, BindingResult bindingResult,  HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateEmployeeCategory()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering PUT Method");
		if(!employeeCategoryService.findById(id).isPresent()){
			logger.info("Id "+id+" is not existed");
			return ResponseEntity.badRequest().build();
		}
		EmployeeCategory getEmpCat = employeeCategoryService.findById(id).get();
		getEmpCat.setCategoryName(employeeCategory.getCategoryName());
		getEmpCat.setCategoryDescription(employeeCategory.getCategoryDescription());
		return ResponseEntity.ok(employeeCategoryService.save(getEmpCat));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id,  HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteEmployeeCategory()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering DELETE Method");
		ResponseMetadata metaData = new ResponseMetadata(ConstantFactory.OK);
		if(!employeeCategoryService.findById(id).isPresent()){
			logger.info("Id "+id+" is not existed");
			return ResponseEntity.badRequest().build();
		}
		employeeCategoryService.deleteById(id);
		
		return ResponseEntity.ok(metaData);
	}
}

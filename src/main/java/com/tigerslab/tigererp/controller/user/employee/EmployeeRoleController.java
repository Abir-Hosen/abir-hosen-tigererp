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

import com.tigerslab.tigererp.controller.AbstractController;
import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.httphelper.ResponseMetadata;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;
import com.tigerslab.tigererp.service.user.employee.EmployeeRoleService;

@RestController
@RequestMapping("/api/employeeroles")
public class EmployeeRoleController extends AbstractController {
	
	@Autowired
	private EmployeeRoleService employeeRoleService;
	@Autowired
	AccessPermission accessPermission;
	
	private Logger logger = LoggerFactory.getLogger(EmployeeRoleController.class);
	
	@GetMapping
	public ResponseEntity<Page<EmployeeRole>> findAllBySortAndOrder(RequestParameter requestParameter, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadRole()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET method");
		Page<EmployeeRole> page = employeeRoleService.findAllBySortAndOrder(requestParameter);
		return ResponseEntity.ok(page);
	}
	
	@PostMapping
	public ResponseEntity<EmployeeRole> create(@RequestBody @Valid EmployeeRole employeeRole, BindingResult bindingResult, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getCreateRole()) {
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
			employeeRoleService.save(employeeRole);
			return ResponseEntity.ok(employeeRole);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<EmployeeRole> findById(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadRole()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET findById");
		Optional<EmployeeRole> employeeRole = employeeRoleService.findById(id);
		if(!employeeRole.isPresent()) {
			logger.info("Id "+id+" is not existed");
			ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(employeeRole.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeRole> update(@PathVariable Long id, @RequestBody @Valid EmployeeRole employeeRole, BindingResult bindingResult, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateRole()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering PUT Method");
		if(!employeeRoleService.findById(id).isPresent()){
			logger.info("Id "+id+" is not existed");
			return ResponseEntity.badRequest().build();
		}
		EmployeeRole getEmpRole = employeeRoleService.findById(id).get();
		getEmpRole.setRoleName(employeeRole.getRoleName());
		getEmpRole.setRoleDescription(employeeRole.getRoleDescription());
		getEmpRole.setUser(employeeRole.getUser());
		return ResponseEntity.ok(employeeRoleService.save(getEmpRole));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteRole()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering DELETE Method");
		ResponseMetadata metaData = new ResponseMetadata(ConstantFactory.OK);
		if(!employeeRoleService.findById(id).isPresent()){
			logger.info("Id "+id+" is not existed");
			return ResponseEntity.badRequest().build();
		}
		employeeRoleService.deleteById(id);
		
		return ResponseEntity.ok(metaData);
	}
}
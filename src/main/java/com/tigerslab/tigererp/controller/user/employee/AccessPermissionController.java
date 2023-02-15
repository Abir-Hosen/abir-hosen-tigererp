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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tigerslab.tigererp.controller.AbstractController;
import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.httphelper.ResponseMetadata;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;
import com.tigerslab.tigererp.service.user.employee.AccessPermissionService;
import com.tigerslab.tigererp.service.user.employee.EmployeeRoleService;

@RestController
@RequestMapping("/api/accesspermission")
public class AccessPermissionController extends AbstractController {
	
	@Autowired
	private AccessPermissionService accessPermissionService;
	
	@Autowired
	private EmployeeRoleService employeeRoleService;
	
	private ResponseMetadata metaData = new ResponseMetadata();
	@Autowired
	AccessPermission accessPermission;
	
	private Logger logger = LoggerFactory.getLogger(AccessPermissionController.class);
	
	
	@PostMapping
	public ResponseEntity<AccessPermission> create(@RequestBody @Valid AccessPermission accessPermissionModel, BindingResult bindingResult, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getCreateAccessPermissionSettings()) {
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
		else { // Valid form
			Long empRoleId = accessPermissionModel.getEmployeeRole().getId();
			Optional<EmployeeRole> employeeRole = employeeRoleService.findById(empRoleId);
			if(!employeeRole.isPresent()) {
				logger.info("Id "+empRoleId+" is not existed");
				return ResponseEntity.badRequest().build();
			}
			accessPermissionModel.setEmployeeRole(employeeRole.get());
			return ResponseEntity.ok(accessPermissionService.save(accessPermissionModel));
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<AccessPermission> findById(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadAccessPermissionSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET findById");
		Optional<AccessPermission> accessPermission = accessPermissionService.findById(id);
		if(!accessPermission.isPresent()) {
			logger.info("Id "+id+" is not existed");
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(accessPermission.get());
	}
	
	@GetMapping
	public ResponseEntity<AccessPermission> findByRoleId(@RequestParam Long roleId, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadAccessPermissionSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET findByRoleId");
		metaData.setMesseges(ConstantFactory.ERROR);
		Optional<AccessPermission> accessPermission = accessPermissionService.findByRoleId(roleId);
		if(!accessPermission.isPresent()) {
			logger.info("Role Id "+roleId+" is not existed in relational table");
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(accessPermission.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AccessPermission> update(@PathVariable Long id, @RequestBody @Valid AccessPermission accessPermissionModel, BindingResult bindingResult, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateAccessPermissionSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering PUT Method");
		if(!accessPermissionService.findById(id).isPresent()){
			logger.info("Id "+id+" is not existed");
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(accessPermissionService.save(accessPermissionModel));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id, @RequestParam Long roleId, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteAccessPermissionSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering DELETE Method"+"--------"+id+" roleID "+roleId);
		ResponseMetadata metaData = new ResponseMetadata(ConstantFactory.OK);
		if(!accessPermissionService.findByIdAndEmployeeRoleId(id, roleId).isPresent()){
			logger.info("Id "+id+" is not existed");
			return ResponseEntity.badRequest().build();
		}
		AccessPermission ap = accessPermissionService.findByIdAndEmployeeRoleId(id, roleId).get();
		System.out.println("-----Ap"+ap);
	//	ap.getEmployeeRole().setId(0);
	//	accessPermissionService.delete(ap);
		
		return ResponseEntity.ok(metaData);
	}
}
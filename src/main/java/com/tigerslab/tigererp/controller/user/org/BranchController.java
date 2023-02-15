package com.tigerslab.tigererp.controller.user.org;

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

import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.Branch;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.service.org.BranchService;

@RestController
@RequestMapping("/api/branch")
public class BranchController {
	
	@Autowired
	private BranchService branchService;

	@Autowired
	AccessPermission accessPermission;
	
	private Logger logger = LoggerFactory.getLogger(BranchController.class);
	
	@GetMapping
	public ResponseEntity<Page<Branch>> findAllBySortAndOrder(RequestParameter requestParameter, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadBranchSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET method");
		Page<Branch> page = branchService.findAllBySortAndOrder(requestParameter);
		return ResponseEntity.ok(page);
	}
	
	@PostMapping
	public ResponseEntity<Branch> create(@RequestBody @Valid Branch branch, BindingResult bindingResult, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getCreateBranchSettings()) {
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
			branchService.save(branch);
			return ResponseEntity.ok(branch);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Branch> findById(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadBranchSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET findById");
		Optional<Branch> SingleBranche = branchService.findById(id);
		if(!SingleBranche.isPresent()) {
			logger.info("Id "+id+" is not existed");
			ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(SingleBranche.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Branch> update(@PathVariable Long id, @RequestBody @Valid Branch branch, BindingResult bindingResult, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateBranchSettings()) {
			return ResponseEntity.badRequest().build();
		}
		
		logger.info("Entering PUT Method");
		logger.info("Branch Info: "+branch);
		StringBuilder serverSideErrors = new StringBuilder("");
		if(bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error : errorList) {
				serverSideErrors.append(", ").append(error.getDefaultMessage());
			}
			logger.error("Form has errors: "+serverSideErrors);
			return ResponseEntity.badRequest().build();
		}else {
			if(!branchService.findById(id).isPresent()) {
				logger.info("Id "+id+" is not existed");
				return ResponseEntity.badRequest().build();
			}else {
				branchService.save(branch);
				return ResponseEntity.ok(branch);
			}
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Branch> delete(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteBranchSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering DELETE Method");
		if(!branchService.findById(id).isPresent()){
			logger.info("Id "+id+" is not existed");
			return ResponseEntity.badRequest().build();
		}
		branchService.deleteById(id);
		
		return ResponseEntity.ok(null);
	}

}

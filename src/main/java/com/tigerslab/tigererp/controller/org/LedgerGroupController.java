package com.tigerslab.tigererp.controller.org;

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
import com.tigerslab.tigererp.model.financial.LedgerGroup;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.httphelper.ResponseMetadata;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.service.org.LedgerGroupService;

@RestController
@RequestMapping("/api/ledgergroup")
public class LedgerGroupController {
	
	@Autowired
	private LedgerGroupService service;
	@Autowired
	AccessPermission accessPermission;
	
	private Logger logger = LoggerFactory.getLogger(LedgerGroupController.class);
	
	@GetMapping
	public ResponseEntity<Page<LedgerGroup>> findAllBySortAndOrder(RequestParameter requestParameter, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadLedgerGroupSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET method");
		Page<LedgerGroup> page = service.findAllBySortAndOrder(requestParameter);
		return ResponseEntity.ok(page);
	}
	
	@PostMapping
	public ResponseEntity<LedgerGroup> create(@RequestBody @Valid LedgerGroup ledgergroup, BindingResult bindingResult, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getCreateLedgerGroupSettings()) {
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
			service.save(ledgergroup);
			return ResponseEntity.ok(ledgergroup);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<LedgerGroup> findById(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadLedgerGroupSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET findById");
		Optional<LedgerGroup> ledgergroupIndiv = service.findById(id);
		if(!ledgergroupIndiv.isPresent()) {
			logger.info("Id "+id+" is not existed");
			ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(ledgergroupIndiv.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<LedgerGroup> update(@PathVariable Long id, @RequestBody @Valid LedgerGroup ledgergroup, BindingResult bindingResult, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateLedgerGroupSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering PUT Method");
		StringBuilder serverSideErrors = new StringBuilder("");
		if(bindingResult.hasErrors()) {
			
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error : errorList) {
				serverSideErrors.append(", ").append(error.getDefaultMessage());
			}
			System.out.println("error1");
			logger.error("Form has errors: "+serverSideErrors);
			return ResponseEntity.badRequest().build();
		}
		else {
//			service.save(ledgergroup);
//			return ResponseEntity.ok(ledgergroup);
			
			if(!service.findById(id).isPresent()){
				System.out.println("error2");
				return ResponseEntity.badRequest().build();
			}
			Optional<LedgerGroup> getLedgerGroup = service.findById(id);
			if(getLedgerGroup.get() != null) {
				System.out.println("error3");
				service.save(ledgergroup);
				return ResponseEntity.ok(ledgergroup);
			}
			else {
				System.out.println("error4");
				return ResponseEntity.badRequest().build();
			}
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteLedgerGroupSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering DELETE Method");
		ResponseMetadata metaData = new ResponseMetadata(ConstantFactory.OK);
		
		Optional<LedgerGroup> getledgergroup =  service.findById(id);
			service.deleteById(id);
			return ResponseEntity.ok(metaData);
	}

}

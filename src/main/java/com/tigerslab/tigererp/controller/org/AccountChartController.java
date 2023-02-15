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
import com.tigerslab.tigererp.model.financial.AccountChart;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.service.financial.AccountChartService;

@RestController
@RequestMapping("/api/accountchart")
public class AccountChartController {
	
	@Autowired
	private AccountChartService service;
	@Autowired
	AccessPermission accessPermission;
	
	private Logger logger = LoggerFactory.getLogger(LedgerGroupController.class);
	
	@GetMapping
	public ResponseEntity<Page<AccountChart>> findAllBySortAndOrder(RequestParameter requestParameter, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadChartOfAccountingSettings()) {
			return ResponseEntity.badRequest().build();
		}
		
		logger.info("Entering GET method");
		Page<AccountChart> page = service.findAllBySortAndOrder(requestParameter);
		return ResponseEntity.ok(page);
	}
	
	@PostMapping
	public ResponseEntity<AccountChart> create(@RequestBody @Valid AccountChart accountChart, BindingResult bindingResult, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getCreateChartOfAccountingSettings()) {
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
			service.save(accountChart);
			return ResponseEntity.ok(accountChart);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<AccountChart> findById(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadChartOfAccountingSettings()) {
			return ResponseEntity.badRequest().build();
		}
		
		logger.info("Entering GET findById");
		Optional<AccountChart> accountChartIndiv = service.findById(id);
		if(!accountChartIndiv.isPresent()) {
			logger.info("Id "+id+" is not existed");
			ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(accountChartIndiv.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<AccountChart> update(@PathVariable Long id, @RequestBody @Valid AccountChart accountChart, BindingResult bindingResult, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateChartOfAccountingSettings()) {
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
			
			if(!service.findById(id).isPresent()){
				System.out.println("error2");
				return ResponseEntity.badRequest().build();
			}
			Optional<AccountChart> getAccountChart = service.findById(id);
			if(getAccountChart.get() != null) {
				System.out.println("error3");
				service.save(accountChart);
				return ResponseEntity.ok(accountChart);
			}
			else {
				System.out.println("error4");
				return ResponseEntity.badRequest().build();
			}
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<AccountChart> delete(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteChartOfAccountingSettings()) {
			return ResponseEntity.badRequest().build();
		}
		
		logger.info("Entering DELETE Method");
		if(!service.findById(id).isPresent()){
			logger.info("Id "+id+" is not existed");
			return ResponseEntity.badRequest().build();
		}
		service.deleteById(id);
		
		return ResponseEntity.ok(null);
	}

}

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

import com.tigerslab.tigererp.controller.AbstractController;
import com.tigerslab.tigererp.controller.user.employee.AccessPermissionController;
import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.Company;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.service.org.CompanyProfileService;

@RestController
@RequestMapping("/api/company")
public class CompanyProfileController extends AbstractController {
	
	@Autowired
	private CompanyProfileService companyProfileService;

	@Autowired
	AccessPermission accessPermission;
	
	private Logger logger = LoggerFactory.getLogger(AccessPermissionController.class);
	
	@PostMapping
	public ResponseEntity<Company> create(@RequestBody @Valid Company company, BindingResult bindingResult, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateCompanySettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering POST Method");
		logger.info("Company Info: "+company);
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
			Long companyId = company.getId();
			Optional<Company> checkCompany = (Optional<Company>) companyProfileService.findById(companyId);
			if(companyId>0) {
				logger.info("Id "+companyId+" is existed. it will update here");
//				companyProfileService.(checkCompany.get());
				update(company, companyId, bindingResult, session);
				return ResponseEntity.ok(company);
			}else {
				companyProfileService.save(company);
				return ResponseEntity.ok(company);
			}
			
		}
	}
	
	@GetMapping
	public ResponseEntity<Page<Company>> countryList(RequestParameter requestParam, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadCompanySettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET method");
		Page<Company> companyList = (Page<Company>) companyProfileService.findAllBySortAndOrder(requestParam);
		return ResponseEntity.ok(companyList);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Company> update(@RequestBody @Valid Company company, @PathVariable Long id, BindingResult bindingResult, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateCompanySettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering PUT Method");
		logger.info("Company Info: "+company);
		StringBuilder serverSideErrors = new StringBuilder("");
		if(bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error : errorList) {
				serverSideErrors.append(", ").append(error.getDefaultMessage());
			}
			logger.error("Form has errors: "+serverSideErrors);
			return ResponseEntity.badRequest().build();
		}else {
			if(!companyProfileService.findById(id).isPresent()) {
				logger.info("Id "+id+" is not existed");
				return ResponseEntity.badRequest().build();
			}else {
				companyProfileService.save(company);
				return ResponseEntity.ok(company);
			}
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Company> delete( @PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteCompanySettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering DELETE Method");
		if(!companyProfileService.findById(id).isPresent()) {
			logger.info("Id "+id+" is not existed");
			return ResponseEntity.badRequest().build();
		}else {
			companyProfileService.deleteById(id);
			return ResponseEntity.ok(null);
		}
	}

	@DeleteMapping()
	public ResponseEntity<Company> deleteAll(HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteCompanySettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering DELETE ALL Method");
		companyProfileService.deleteAll();
		return ResponseEntity.ok(null);
	}

}

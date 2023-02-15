package com.tigerslab.tigererp.controller.org;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
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
import com.tigerslab.tigererp.model.Role;
import com.tigerslab.tigererp.model.User;
import com.tigerslab.tigererp.model.UserExceptPassword;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.httphelper.ResponseMetadata;
import com.tigerslab.tigererp.model.org.StockCategory;
import com.tigerslab.tigererp.model.org.inventory.Units;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;
import com.tigerslab.tigererp.service.org.inventory.UnitsService;
import com.tigerslab.tigererp.service.user.UserService;
import com.tigerslab.tigererp.service.user.employee.EmployeeRoleService;

@RestController
@RequestMapping("/api/units")
public class UnitsController extends AbstractController {
	
	@Autowired
	private UnitsService unitsService;
	@Autowired
	AccessPermission accessPermission;
	
	private Logger logger = LoggerFactory.getLogger(UnitsController.class);
	
	@GetMapping
	public ResponseEntity<Page<Units>> findAllBySortAndOrder(RequestParameter requestParameter, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadUnitMeasure()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET method");
		Page<Units> page = unitsService.findAllBySortAndOrder(requestParameter);
		return ResponseEntity.ok(page);
	}
	
	@PostMapping
	public ResponseEntity<Units> create(@RequestBody @Valid Units unit, BindingResult bindingResult, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getCreateUnitMeasure()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering POST Method");
		StringBuilder serverSideErrors = new StringBuilder("");
		if(bindingResult.hasErrors()) {
			System.out.println("1");
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error : errorList) {
				serverSideErrors.append(", ").append(error.getDefaultMessage());
			}
			System.out.println("2");
			logger.error("Form has errors: "+serverSideErrors);
			return ResponseEntity.badRequest().build();
		}
		else {
			System.out.println(unit);
			unitsService.save(unit);
			
			return ResponseEntity.ok(unit);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Units> findById(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadUnitMeasure()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET findById");
		Optional<Units> unitIndiv = unitsService.findById(id);
		if(!unitIndiv.isPresent()) {
			logger.info("Id "+id+" is not existed");
			ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(unitIndiv.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Units> update(@PathVariable Long id, @RequestBody @Valid Units unit, BindingResult bindingResult, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateUnitMeasure()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering PUT Method");
		if(!unitsService.findById(id).isPresent()){
			return ResponseEntity.badRequest().build();
		}
		Optional<Units> getUnits = unitsService.findById(id);
		if(getUnits.get() != null) {
			if(unit.getFirstUnit() != null) {
				Units unitUpdate = getUnits.get();
				Optional<Units> getFirstUnit = unitsService.findById(unit.getFirstUnit().getId());
				Optional<Units> getSecondUnit = unitsService.findById(unit.getSecondUnit().getId());
				if(getFirstUnit.get() != null && getSecondUnit.get() != null) {
					unitUpdate.setFirstUnit(getFirstUnit.get());
					unitUpdate.setSecondUnit(getSecondUnit.get());
					unitUpdate.setValue(unit.getValue());
					unitUpdate.setDescription(unit.getDescription());
					System.out.println("Call compund: "+unitUpdate);
					return ResponseEntity.ok(unitsService.save(unitUpdate));
				}
			}
			System.out.println("call simple: "+unit);
			return ResponseEntity.ok(unitsService.save(unit));
		}
		else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteUnitMeasure()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering DELETE Method");
		ResponseMetadata metaData = new ResponseMetadata(ConstantFactory.OK);
		
		Optional<Units> getUnit =  unitsService.findById(id);
			unitsService.deleteById(id);
			return ResponseEntity.ok(metaData);
	}
}

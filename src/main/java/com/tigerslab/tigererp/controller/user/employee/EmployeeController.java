package com.tigerslab.tigererp.controller.user.employee;

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

import com.tigerslab.tigererp.controller.AbstractController;
import com.tigerslab.tigererp.controller.org.LedgerAccountsController;
import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.Role;
import com.tigerslab.tigererp.model.User;
import com.tigerslab.tigererp.model.UserExceptPassword;
import com.tigerslab.tigererp.model.financial.LedgerAccounts;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.httphelper.ResponseMetadata;
import com.tigerslab.tigererp.model.org.Branch;
import com.tigerslab.tigererp.model.user.AddressV2;
import com.tigerslab.tigererp.model.user.Country;
import com.tigerslab.tigererp.model.user.PhoneFormatFullCountry;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.model.user.employee.Employee;
import com.tigerslab.tigererp.model.user.employee.EmployeeCategory;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;
import com.tigerslab.tigererp.service.financial.LedgerAccountsService;
import com.tigerslab.tigererp.service.org.BranchService;
import com.tigerslab.tigererp.service.org.CountryService;
import com.tigerslab.tigererp.service.org.PhoneFormatFullCountryService;
import com.tigerslab.tigererp.service.user.UserService;
import com.tigerslab.tigererp.service.user.employee.AddressV2Service;
import com.tigerslab.tigererp.service.user.employee.EmployeeCategoryService;
import com.tigerslab.tigererp.service.user.employee.EmployeeRoleService;
import com.tigerslab.tigererp.service.user.employee.EmployeeService;

@Transactional
@RestController
@RequestMapping("/api/employee")
public class EmployeeController extends AbstractController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmployeeRoleService employeeRoleService;
	
	@Autowired
	private EmployeeService employeeService;	
	
	@Autowired
	private AddressV2Service addressV2Service;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private PhoneFormatFullCountryService phoneFormatFullCountryService;
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private EmployeeCategoryService employeeCategoryService;

	@Autowired
	AccessPermission accessPermission;

	@Autowired
	LedgerAccountsService ledgerAccountsService;

	@Autowired
	LedgerAccountsController ledgerAccountsController;
	
	private Logger logger = LoggerFactory.getLogger(EmployeeRoleController.class);
	
	@GetMapping
	public ResponseEntity<Page<UserExceptPassword>> findAllBySortAndOrder(RequestParameter requestParameter, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadEmployee()) {
			return ResponseEntity.badRequest().build();
		}
		Page<UserExceptPassword> page = null;
		try {
			page = userService.findByRoles(ConstantFactory.ROLE_EMPLOYEE, true, requestParameter);
		}catch(Exception ex) {
			System.out.println("Exception "+ex.getMessage());
			ex.printStackTrace();
		}
		return ResponseEntity.ok(page);
	}
	
	@PostMapping
	public ResponseEntity<User> create(@RequestBody @Valid User user, BindingResult bindingResult, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getCreateEmployee()) {
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
			LedgerAccounts ledgerAccounts = new LedgerAccounts();
			ledgerAccounts = user.getEmployee().getLedgerAccounts();
			
			ledgerAccounts.setAccountName(user.getFirstName()+ " "+ user.getLastName());
			ledgerAccounts.setAlius(user.getEmployee().getAlius());
			ledgerAccounts.setCreationDate(user.getEmployee().getJoiningDate());
			ledgerAccounts.setDescription(user.getEmployee().getDescription());
			ledgerAccounts.setDob(user.getEmployee().getDateOfBirth());
			ledgerAccounts.setFathersName(user.getEmployee().getFatherName());
			ledgerAccounts.setMothersName(user.getEmployee().getMotherName());
			ledgerAccounts.setSpouceName(user.getEmployee().getSpouseName());
			ledgerAccounts.setNid(user.getEmployee().getNid());
			ledgerAccounts.setGender(user.getEmployee().getGender());
			ledgerAccounts.setAccountAddress(user.getEmployee().getEmployeeAddress());

			LedgerAccounts ledgerAccount = ledgerAccountsService.saveWithTransactions(ledgerAccounts);			
			user.getEmployee().setLedgerAccounts(ledgerAccount);

			userService.saveUser(user, ConstantFactory.ROLE_EMPLOYEE);
			return ResponseEntity.ok(user);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid User user, BindingResult bindingResult, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateEmployee()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering PUT Method");
		System.out.println("USER REQUEST ID: "+id);
		System.out.println("USER REQUEST IS: "+user);
		Optional<User> u = userService.findById(id);
		if(!u.isPresent()){
			logger.info("Id "+id+" is not existed for User");
			return ResponseEntity.badRequest().build();
		}
		User userUpdate = u.get();
		
		//User Role
//		long roleId = 0;
//		roleId = user.getEmpRoles().iterator().next().getId();
//		Optional<EmployeeRole> checkEmployeeRole = employeeRoleService.findById(roleId);
//		if(!checkEmployeeRole.isPresent()) {
//			logger.info("Id "+roleId+" is not existed for User Role");
//			return ResponseEntity.badRequest().build();
//		}
//		Set<EmployeeRole> t = new HashSet<EmployeeRole>();
//		t.add(checkEmployeeRole.get());
		//Employee 
		Optional<Employee> checkEmployeee = employeeService.findById(user.getEmployee().getId());
		if(!checkEmployeee.isPresent()) {
			return ResponseEntity.badRequest().build();
		}		
		Employee emp = checkEmployeee.get();
		//Employee Branch
		Optional<Branch> checkBranch= branchService.findById(user.getEmployee().getBranch().iterator().next().getId());
		if(!checkBranch.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		Branch branch = checkBranch.get();
		//Employee Address
		Optional<AddressV2> checkAddress = addressV2Service.findById(user.getEmployee().getEmployeeAddress().iterator().next().getId());
		if(!checkAddress.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		AddressV2 addr = checkAddress.get();
		//Employee Category
		Optional<EmployeeCategory> checkCat= employeeCategoryService.findById(user.getEmployee().getEmployeeCategory().iterator().next().getId());
		if(!checkAddress.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		EmployeeCategory empCat = checkCat.get();
		//Address Country
		Optional<Country> checkCountry = countryService.findById(user.getEmployee().getEmployeeAddress().iterator().next().getAddressCountry().getId());
		if(!checkCountry.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		Country coun = checkCountry.get();
		//Address Phone
		Optional<PhoneFormatFullCountry> checkPhone = null;
		PhoneFormatFullCountry phone = null;
		if(user.getEmployee().getEmployeeAddress().iterator().next().getPhone()!=null) {
			if(user.getEmployee().getEmployeeAddress().iterator().next().getPhone().getId()==null) {
				phone = phoneFormatFullCountryService.save(user.getEmployee().getEmployeeAddress().iterator().next().getPhone());
			}else {
				checkPhone = phoneFormatFullCountryService.findById(user.getEmployee().getEmployeeAddress().iterator().next().getPhone().getId());
			}
		}
		if(checkPhone!=null) {
			phone = checkPhone.get();

			phone.setCountry(user.getEmployee().getEmployeeAddress().iterator().next().getPhone().getCountry());
			phone.setNumber(user.getEmployee().getEmployeeAddress().iterator().next().getPhone().getNumber());
		}
		//Address Fax
		Optional<PhoneFormatFullCountry> checkfax = null;
		PhoneFormatFullCountry fax = null;
		if(user.getEmployee().getEmployeeAddress().iterator().next().getFax()!=null) {
			if(user.getEmployee().getEmployeeAddress().iterator().next().getFax().getId()==null) {
				fax = phoneFormatFullCountryService.save(user.getEmployee().getEmployeeAddress().iterator().next().getFax());
			}else {
				checkfax = phoneFormatFullCountryService.findById(user.getEmployee().getEmployeeAddress().iterator().next().getFax().getId());
			}
		}
		if(checkfax!=null) {
			fax = checkfax.get();

			fax.setCountry(user.getEmployee().getEmployeeAddress().iterator().next().getFax().getCountry());
			fax.setNumber(user.getEmployee().getEmployeeAddress().iterator().next().getFax().getNumber());
		}
		//Address Mobile
		Optional<PhoneFormatFullCountry> checkMoblile = null;
		PhoneFormatFullCountry mobile = null;
		if(user.getEmployee().getEmployeeAddress().iterator().next().getMobilePhone()!=null) {
			if(user.getEmployee().getEmployeeAddress().iterator().next().getMobilePhone().getId()==null) {
				mobile = phoneFormatFullCountryService.save(user.getEmployee().getEmployeeAddress().iterator().next().getMobilePhone());
			}else {
				checkMoblile = phoneFormatFullCountryService.findById(user.getEmployee().getEmployeeAddress().iterator().next().getMobilePhone().getId());
			}
		}
		if(checkMoblile!=null) {
			mobile = checkMoblile.get();

			mobile.setCountry(user.getEmployee().getEmployeeAddress().iterator().next().getMobilePhone().getCountry());
			mobile.setNumber(user.getEmployee().getEmployeeAddress().iterator().next().getMobilePhone().getNumber());
		}
		//Address Alt Phone
		Optional<PhoneFormatFullCountry> checkAltPhone = null;
		PhoneFormatFullCountry altPhone = null;
		if(user.getEmployee().getEmployeeAddress().iterator().next().getAltMobilePhone()!=null) {
			if(user.getEmployee().getEmployeeAddress().iterator().next().getAltMobilePhone().getId()==null) {
				altPhone = phoneFormatFullCountryService.save(user.getEmployee().getEmployeeAddress().iterator().next().getAltMobilePhone());
			}else {
				checkAltPhone = phoneFormatFullCountryService.findById(user.getEmployee().getEmployeeAddress().iterator().next().getAltMobilePhone().getId());
			}
		}
		if(checkAltPhone!=null) {
			altPhone = checkAltPhone.get();

			altPhone.setCountry(user.getEmployee().getEmployeeAddress().iterator().next().getAltMobilePhone().getCountry());
			altPhone.setNumber(user.getEmployee().getEmployeeAddress().iterator().next().getAltMobilePhone().getNumber());
		}

		addr.setContactPersonName(user.getEmployee().getEmployeeAddress().iterator().next().getContactPersonName());
		addr.setAddressLine1(user.getEmployee().getEmployeeAddress().iterator().next().getAddressLine1());
		addr.setAddressLine2(user.getEmployee().getEmployeeAddress().iterator().next().getAddressLine2());
		addr.setCity(user.getEmployee().getEmployeeAddress().iterator().next().getCity());
		addr.setZip(user.getEmployee().getEmployeeAddress().iterator().next().getZip());
		addr.setEmail(user.getEmployee().getEmployeeAddress().iterator().next().getEmail());
		addr.setWebsite(user.getEmployee().getEmployeeAddress().iterator().next().getWebsite());
		addr.setAddressCountry(user.getEmployee().getEmployeeAddress().iterator().next().getAddressCountry());//Address ->Country
		addr.setPhone(phone);//Address ->Phone
		addr.setFax(fax);//Address ->Fax
		addr.setMobilePhone(mobile);//Address ->Mobile
		addr.setAltMobilePhone(altPhone);//Address ->Alt Phone
		
		emp.setAlius(user.getEmployee().getAlius());
		emp.setUserName(user.getEmployee().getUserName());
		emp.setJoiningDate(user.getEmployee().getJoiningDate());
		emp.setDescription(user.getEmployee().getDescription());
		emp.setDateOfBirth(user.getEmployee().getDateOfBirth());
		emp.setFatherName(user.getEmployee().getFatherName());
		emp.setMotherName(user.getEmployee().getMotherName());
		emp.setSpouseName(user.getEmployee().getSpouseName());
		emp.setNid(user.getEmployee().getNid());
		emp.setEmployeeAddress(new HashSet<AddressV2>(Arrays.asList(addr)));//Employee ->Address
		emp.setEmployeeCategory(new HashSet<EmployeeCategory>(Arrays.asList(empCat)));//Employee ->Category
		emp.setBranch(new HashSet<Branch>(Arrays.asList(branch)));//Employee ->Branch
		
		
		userUpdate.setFirstName(user.getFirstName().trim());
		userUpdate.setLastName(user.getLastName().trim());
		userUpdate.setEmail(user.getEmail().trim());
		userUpdate.setEmployee(emp);//User ->Employee
		
		//userService.saveUser(userUpdate, ConstantFactory.ROLE_EMPLOYEE);
		userService.save(userUpdate);
		System.out.println("---------------------------------------------\n");
		//System.out.println(userUpdate);
		
		return ResponseEntity.ok(userUpdate);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteEmployee()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering DELETE Method "+id);
		ResponseMetadata metaData = new ResponseMetadata(ConstantFactory.OK);
		Optional<User> userCheck = userService.findById(id);
		if(!userCheck.isPresent()){
			logger.info("Id "+id+" is not existed for User");
			return ResponseEntity.badRequest().build();
		}
		User user = userCheck.get();
		userService.deleteUser(user);
		
		return ResponseEntity.ok(metaData);
	}

}

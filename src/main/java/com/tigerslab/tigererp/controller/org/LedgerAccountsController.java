package com.tigerslab.tigererp.controller.org;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import com.tigerslab.tigererp.config.SMSTemplate;
import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.financial.LedgerAccounts;
import com.tigerslab.tigererp.model.financial.LedgerGroup;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.user.AddressV2;
import com.tigerslab.tigererp.model.user.Country;
import com.tigerslab.tigererp.model.user.PhoneFormatFullCountry;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.service.financial.LedgerAccountsService;
import com.tigerslab.tigererp.service.financial.LedgerEntriesService;
import com.tigerslab.tigererp.service.financial.TransactionEntryTypeService;
import com.tigerslab.tigererp.service.org.CountryService;
import com.tigerslab.tigererp.service.org.LedgerGroupService;
import com.tigerslab.tigererp.service.org.PhoneFormatFullCountryService;
import com.tigerslab.tigererp.service.sms.SendSMSInfoService;
import com.tigerslab.tigererp.service.user.employee.AddressV2Service;
import com.tigerslab.tigererp.sms.TigerERPSMSSender;

	
@Transactional
@RestController
@RequestMapping("/api/ledgerAccounts")
public class LedgerAccountsController {
	
	@Autowired
	private LedgerAccountsService service;
	
	@Autowired
	private LedgerGroupService ledgerGroupService;

	@Autowired
	private AddressV2Service addressV2Service;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private PhoneFormatFullCountryService phoneFormatFullCountryService;
	
	@Autowired
	private TransactionEntryTypeService transactionEntryTypeService;
	
	@Autowired
	private LedgerEntriesService ledgerEntriesService;
	
	@Autowired
	private TigerERPSMSSender sms;
	
	@Autowired
	private SMSTemplate smsTemplate;
	@Autowired
	AccessPermission accessPermission;
	
	@Autowired
	private SendSMSInfoService sendSMSInfoService;
	
	
	private Logger logger = LoggerFactory.getLogger(LedgerAccountsController.class);
	
	@GetMapping
	public ResponseEntity<Page<LedgerAccounts>> findAllBySortAndOrder(RequestParameter requestParameter, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadLedgerSettings()) {
			return ResponseEntity.badRequest().build();
		}
		
		logger.info("Entering GET method");
		Page<LedgerAccounts> page = service.findAllBySortAndOrder(requestParameter);
		
		return ResponseEntity.ok(page);
	}
	
	@GetMapping("/balance")
	public ResponseEntity<Page<LedgerAccounts>> findAllLedgerAccountsWithBalance(RequestParameter requestParameter, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadLedgerSettings()) {
			return ResponseEntity.badRequest().build();
		}
		
		logger.info("Entering GET method");
		Page<LedgerAccounts> page = service.findAllLedgerAccountsWithBalance(requestParameter);
		
		return ResponseEntity.ok(page);
	}
	
//	@GetMapping("/accounts/{id}")
//	public ResponseEntity<Page<LedgerAccounts>> findById(@PathVariable Long id, RequestParameter requestParameter) {
//		logger.info("Entering GET method");
//		Page<LedgerAccounts> page = service.findByAccountIdAndCustomQuery(id, requestParameter);
//		
//		return ResponseEntity.ok(page);
//	}
	
	
	@PostMapping
	public ResponseEntity<LedgerAccounts> create(@RequestBody @Valid LedgerAccounts ledgerAccounts, BindingResult bindingResult, HttpSession session) {

		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getCreateLedgerSettings()) {
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
			LedgerAccounts newAcc = service.saveWithTransactions(ledgerAccounts);
			if(newAcc != null) {
				// send welcome SMS
				String mobileNum = newAcc.getAccountAddress().iterator().next().getMobilePhone().getNumber();
				String countryCode = "+"+newAcc.getAccountAddress().iterator().next().getMobilePhone().getCountry().getPhoneCode();
				String fullNumber = countryCode.concat(mobileNum);
				System.out.println("------- TRYING SEND SMS TO: "+fullNumber);
				if(ledgerAccounts.getSendSMS()) {
					try {
						sms.SendSMS(fullNumber, smsTemplate.getLedgerAccountCreationTemplate());
						//save sms sending info to DB for future track
						sms.saveSMSInfo(fullNumber, smsTemplate.getLedgerAccountCreationTemplate(), newAcc);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return ResponseEntity.ok(newAcc);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<LedgerAccounts> findById(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadLedgerSettings()) {
			return ResponseEntity.badRequest().build();
		}
		
		logger.info("Entering GET findById");
		Optional<LedgerAccounts> ledgerAccounts = service.findById(id);
		if(!ledgerAccounts.isPresent()) {
			logger.info("Id "+id+" is not existed");
			ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(ledgerAccounts.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<LedgerAccounts> update(@PathVariable Long id, @RequestBody @Valid LedgerAccounts ledgerAccounts, BindingResult bindingResult, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateLedgerSettings()) {
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
			Optional<LedgerAccounts> getLedgerAccounts = service.findById(id);
			if(getLedgerAccounts.get() != null) {
				LedgerAccounts updateLedgerAccount = getLedgerAccounts.get();

				//Ledger Group
				Optional<LedgerGroup> checkGroup= ledgerGroupService.findById(ledgerAccounts.getAccountGroup().iterator().next().getId());
				if(!checkGroup.isPresent()) {
					return ResponseEntity.badRequest().build();
				}
				LedgerGroup grp  = checkGroup.get();
				
				
				//Ledger Address
				Optional<AddressV2> checkAddress = addressV2Service.findById(ledgerAccounts.getAccountAddress().iterator().next().getId());
				if(!checkAddress.isPresent()) {
					System.out.println("Hello problem!");
					return ResponseEntity.badRequest().build();
				}
				AddressV2 addr = checkAddress.get();
				//Address Country
				Optional<Country> checkCountry = countryService.findById(ledgerAccounts.getAccountAddress().iterator().next().getAddressCountry().getId());
				if(!checkCountry.isPresent()) {
					return ResponseEntity.badRequest().build();
				}
				Country coun = checkCountry.get();
				//Address Phone
				Optional<PhoneFormatFullCountry> checkPhone = null;
				PhoneFormatFullCountry phone = null;
				if(ledgerAccounts.getAccountAddress().iterator().next().getPhone()!=null) {
					if(ledgerAccounts.getAccountAddress().iterator().next().getPhone().getId()==null) {
						phone = phoneFormatFullCountryService.save(ledgerAccounts.getAccountAddress().iterator().next().getPhone());
					}else {
						checkPhone = phoneFormatFullCountryService.findById(ledgerAccounts.getAccountAddress().iterator().next().getPhone().getId());
					}
				}
				if(checkPhone!=null) {
					phone = checkPhone.get();

					phone.setCountry(ledgerAccounts.getAccountAddress().iterator().next().getPhone().getCountry());
					phone.setNumber(ledgerAccounts.getAccountAddress().iterator().next().getPhone().getNumber());
				}
				//Address Fax
				Optional<PhoneFormatFullCountry> checkfax = null;
				PhoneFormatFullCountry fax = null;
				if(ledgerAccounts.getAccountAddress().iterator().next().getFax()!=null) {
					if(ledgerAccounts.getAccountAddress().iterator().next().getFax().getId()==null) {
						fax = phoneFormatFullCountryService.save(ledgerAccounts.getAccountAddress().iterator().next().getFax());
					}else {
						checkfax = phoneFormatFullCountryService.findById(ledgerAccounts.getAccountAddress().iterator().next().getFax().getId());
					}
				}
				if(checkfax!=null) {
					fax = checkfax.get();

					fax.setCountry(ledgerAccounts.getAccountAddress().iterator().next().getFax().getCountry());
					fax.setNumber(ledgerAccounts.getAccountAddress().iterator().next().getFax().getNumber());
				}
				//Address Mobile
				Optional<PhoneFormatFullCountry> checkMoblile = null;
				PhoneFormatFullCountry mobile = null;
				if(ledgerAccounts.getAccountAddress().iterator().next().getMobilePhone()!=null) {
					if(ledgerAccounts.getAccountAddress().iterator().next().getMobilePhone().getId()==null) {
						mobile = phoneFormatFullCountryService.save(ledgerAccounts.getAccountAddress().iterator().next().getMobilePhone());
					}else {
						checkMoblile = phoneFormatFullCountryService.findById(ledgerAccounts.getAccountAddress().iterator().next().getMobilePhone().getId());
					}
				}
				if(checkMoblile!=null) {
					mobile = checkMoblile.get();

					mobile.setCountry(ledgerAccounts.getAccountAddress().iterator().next().getMobilePhone().getCountry());
					mobile.setNumber(ledgerAccounts.getAccountAddress().iterator().next().getMobilePhone().getNumber());
				}
				//Address Alt Phone
				Optional<PhoneFormatFullCountry> checkAltPhone = null;
				PhoneFormatFullCountry altPhone = null;
				if(ledgerAccounts.getAccountAddress().iterator().next().getAltMobilePhone()!=null) {
					if(ledgerAccounts.getAccountAddress().iterator().next().getAltMobilePhone().getId()==null) {
						altPhone = phoneFormatFullCountryService.save(ledgerAccounts.getAccountAddress().iterator().next().getAltMobilePhone());
					}else {
						checkAltPhone = phoneFormatFullCountryService.findById(ledgerAccounts.getAccountAddress().iterator().next().getAltMobilePhone().getId());
					}
				}
				if(checkAltPhone!=null) {
					altPhone = checkAltPhone.get();

					altPhone.setCountry(ledgerAccounts.getAccountAddress().iterator().next().getAltMobilePhone().getCountry());
					altPhone.setNumber(ledgerAccounts.getAccountAddress().iterator().next().getAltMobilePhone().getNumber());
				}
				addr.setContactPersonName(ledgerAccounts.getAccountAddress().iterator().next().getContactPersonName());
				addr.setAddressLine1(ledgerAccounts.getAccountAddress().iterator().next().getAddressLine1());
				addr.setAddressLine2(ledgerAccounts.getAccountAddress().iterator().next().getAddressLine2());
				addr.setCity(ledgerAccounts.getAccountAddress().iterator().next().getCity());
				addr.setZip(ledgerAccounts.getAccountAddress().iterator().next().getZip());
				addr.setEmail(ledgerAccounts.getAccountAddress().iterator().next().getEmail());
				addr.setWebsite(ledgerAccounts.getAccountAddress().iterator().next().getWebsite());
				addr.setAddressCountry(ledgerAccounts.getAccountAddress().iterator().next().getAddressCountry());//Address ->Country
				addr.setPhone(phone);//Address ->Phone
				addr.setFax(fax);//Address ->Fax
				addr.setMobilePhone(mobile);//Address ->Mobile
				addr.setAltMobilePhone(altPhone);//Address ->Alt Phone
				
				
				updateLedgerAccount.setAccountName(ledgerAccounts.getAccountName().trim());
				updateLedgerAccount.setAlius(ledgerAccounts.getAlius().trim());
				updateLedgerAccount.setLedgerCode(ledgerAccounts.getLedgerCode().trim());
				updateLedgerAccount.setCreationDate(ledgerAccounts.getCreationDate());
				updateLedgerAccount.setDescription(ledgerAccounts.getDescription().trim());
				updateLedgerAccount.setDob(ledgerAccounts.getDob());
				updateLedgerAccount.setFathersName(ledgerAccounts.getFathersName().trim());
				updateLedgerAccount.setMothersName(ledgerAccounts.getMothersName().trim());
				updateLedgerAccount.setSpouceName(ledgerAccounts.getSpouceName().trim());
				updateLedgerAccount.setNid(ledgerAccounts.getNid().trim());
				updateLedgerAccount.setOpeningBalance(ledgerAccounts.getOpeningBalance());
				updateLedgerAccount.setAccountLimit(ledgerAccounts.getAccountLimit());
				
				updateLedgerAccount.setParentAccount(ledgerAccounts.getParentAccount());//-----
				updateLedgerAccount.setAccountGroup(new ArrayList<LedgerGroup>(Arrays.asList(grp)));
				updateLedgerAccount.setAccountAddress(new HashSet<AddressV2>(Arrays.asList(addr)));//-----
//				updateLedgerAccount.setAccountGroup(new HashSet<LedgerGroup>(Arrays.asList(grp)));//-----
//				updateLedgerAccount.setAccountAddress(new HashSet<AddressV2>(Arrays.asList(addr)));//-----
				
				System.out.println("error3");
				service.save(updateLedgerAccount);
				return ResponseEntity.ok(updateLedgerAccount);
			}
			else {
				System.out.println("error4");
				return ResponseEntity.badRequest().build();
			}
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<LedgerAccounts> delete(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteLedgerSettings()) {
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

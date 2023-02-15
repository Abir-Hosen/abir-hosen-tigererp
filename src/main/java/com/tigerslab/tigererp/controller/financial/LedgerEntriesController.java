package com.tigerslab.tigererp.controller.financial;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.User;
import com.tigerslab.tigererp.model.financial.LedgerAccounts;
import com.tigerslab.tigererp.model.financial.LedgerEntries;
import com.tigerslab.tigererp.model.financial.LedgerGroup;
import com.tigerslab.tigererp.model.financial.VoucherType;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.Company;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.repository.financial.LedgerAccountsRepository;
import com.tigerslab.tigererp.service.financial.LedgerAccountsService;
import com.tigerslab.tigererp.service.financial.LedgerEntriesService;
import com.tigerslab.tigererp.service.financial.TransactionService;
import com.tigerslab.tigererp.service.financial.VoucherTypeService;
import com.tigerslab.tigererp.service.org.CompanyProfileService;
import com.tigerslab.tigererp.service.user.UserService;
import com.tigerslab.tigererp.sms.TigerERPSMSSender;

@RestController
@RequestMapping("/api/ledgerentries")
public class LedgerEntriesController {
	
	@Autowired
	private LedgerEntriesService ledgerEntriesService;
	@Autowired
	private LedgerAccountsService ledgerAccountsService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private UserService userService;
	@Autowired
	private VoucherTypeService voucherTypeService;
	@Autowired
	AccessPermission accessPermission;
	
	@Autowired
	private LedgerAccountsRepository ledgerAccountsRepository;
	
	@Autowired
	private TigerERPSMSSender tigerERPSMSSender;
	
	@Autowired
	private CompanyProfileService companyProfileService;
	
	@RequestMapping(value="/account/{id}", method = RequestMethod.GET)
	public ResponseEntity<Page<LedgerAccounts>> findByAccountId(@PathVariable Long id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate, RequestParameter requestParameter, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadLedgerEntriesSettings()) {
			return ResponseEntity.badRequest().build();
		}
		
		if(startDate !=null) {
			startDate.setDate(startDate.getDate()+1);
		}if(endDate !=null) {
			endDate.setDate(endDate.getDate()+1);
		}
		
		Date currentDate = new Date();
		Calendar calender = Calendar.getInstance();
		
		System.out.println("Id: "+id+" Param: "+requestParameter);
        if(startDate == null) {
        	calender.setTime(currentDate);
        	calender.set(1700, 10, 10);
        	startDate = calender.getTime();
        }
        if(endDate == null) {
        	calender.setTime(currentDate);
        	calender.add(calender.DATE, 2);
        	endDate = calender.getTime();
        }
        System.out.println("START DATE: "+startDate+"End DAte: "+endDate);
		Page<LedgerAccounts> accountTransaction = ledgerAccountsService.findByAccountIdAndCustomQuery(id, startDate, endDate, requestParameter);
		//System.out.println(accountTransaction.getContent());
		return ResponseEntity.ok(accountTransaction);
	}
	
	@RequestMapping(value="/voucher/{voucherTypeId}", method = RequestMethod.GET)
	public ResponseEntity<Page<LedgerEntries>> findByVoucher(@PathVariable Long voucherTypeId,  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate, RequestParameter requestParameter, HttpSession session) {
	
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if((!accessPermission.getReadAllVoucherSettings()) 
				|| (!accessPermission.getReadRecepientVoucherSettings() && voucherTypeId== (long)1) 
				|| (!accessPermission.getReadPaymentVoucherSettings() && voucherTypeId== (long)2)
				|| (!accessPermission.getReadContraVoucherSettings() && voucherTypeId== (long)3) 
				|| (!accessPermission.getReadExpenseVoucherSettings() && voucherTypeId== (long)4)
				|| (!accessPermission.getReadJournalVoucherSettings() && voucherTypeId== (long)5)) {
			return ResponseEntity.badRequest().build();
		}
		startDate.setDate(startDate.getDate()+1);
		endDate.setDate(endDate.getDate()+1);
		System.out.println("Id: "+voucherTypeId+" Param: "+requestParameter);
		Page<LedgerEntries> ledgerEntries;
		if(voucherTypeId==0) {
			ledgerEntries =  ledgerEntriesService.findByVoucherType(startDate, endDate, requestParameter);
		}else {
			Optional<VoucherType> voucherType = voucherTypeService.findById(voucherTypeId);
			VoucherType voucherType2 = new VoucherType();
			if(voucherType!=null) {
				voucherType2=voucherType.get();
			}else {
				return ResponseEntity.badRequest().build();
			}
			ledgerEntries =  ledgerEntriesService.findByVoucher(voucherTypeId, startDate, endDate, requestParameter);
		}
		return ResponseEntity.ok(ledgerEntries);
	}
	
	@RequestMapping(value="/account", method = RequestMethod.POST)
	public ResponseEntity<Page<LedgerEntries>> postingJournalVoucher(@RequestBody LedgerEntries [] ledgerList, HttpSession session) {
		
		Long voucherTypeId = ledgerList[0].getVoucherType().getId();
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if((!accessPermission.getCreateAllVoucherSettings()) 
				|| (!accessPermission.getCreateRecepientVoucherSettings() && voucherTypeId== (long)1) 
				|| (!accessPermission.getCreatePaymentVoucherSettings() && voucherTypeId== (long)2)
				|| (!accessPermission.getCreateContraVoucherSettings() && voucherTypeId== (long)3) 
				|| (!accessPermission.getCreateExpenseVoucherSettings() && voucherTypeId== (long)4)
				|| (!accessPermission.getCreateJournalVoucherSettings() && voucherTypeId== (long)5)) {
			return ResponseEntity.badRequest().build();
		}
		
		System.out.println("POST Method");
		User user  = userService.findUserByEmail((String) session.getAttribute("userName"));
		LedgerEntries ledgerEntries[] = new LedgerEntries[2];
		for(int i=0; i<ledgerList.length; i++) {
			ledgerEntries[i]=ledgerList[i];
			ledgerEntries[i].setCreatedBy(user);
		}
		
		Optional<LedgerAccounts> firstLedger = null;
		Optional<LedgerAccounts> secondLedger = null;
		BigDecimal previousBalanceFirstAccount = new BigDecimal(0);
		BigDecimal smsPreviousBalanceFirstAccount = new BigDecimal(0);
		BigDecimal previousBalanceSecondAccount = new BigDecimal(0);
		
		if(ledgerList[0].getLedgerAccounts() != null) {
			firstLedger = ledgerAccountsService.findById((long)ledgerList[0].getLedgerAccounts().getId());
			previousBalanceFirstAccount = ledgerAccountsRepository.findByLedgerAccountsWithBalance(firstLedger.get().getId());
		}
		if(ledgerList[0].getSecondLedgerAccounts() != null) {
			secondLedger = ledgerAccountsService.findById((long)ledgerList[0].getSecondLedgerAccounts().getId());
			previousBalanceSecondAccount = ledgerAccountsRepository.findByLedgerAccountsWithBalance(secondLedger.get().getId());
		}
		
		
		transactionService.makeDoubleEntryTransaction(ledgerEntries);
		// Preparing to Send SMS
		boolean isInternalAccount = false;
		boolean isInternalAccountSecond = false;
		String mobileNum = "";
		String countryCode = "";
		String fullNumber = "";
		String companyName = "";
		Optional<Company> company = companyProfileService.findById((long)1);
		if(company.isPresent()) {
			companyName = company.get().getCompanyName();
			if(companyName.length() > 40) {
				companyName = companyName.substring(0, 39);
			}
		}
		
		if(firstLedger.isPresent()) {
			LedgerAccounts account1 = firstLedger.get();
			
			if(account1.getAccountAddress().iterator().hasNext()) {
				mobileNum = account1.getAccountAddress().iterator().next().getMobilePhone().getNumber();
				countryCode = "+"+account1.getAccountAddress().iterator().next().getMobilePhone().getCountry().getPhoneCode();
				fullNumber = countryCode.concat(mobileNum);
			}
			
			BigDecimal tranAmount = new BigDecimal(0);
			BigDecimal smsTranAmount = new BigDecimal(0);
			tranAmount = ledgerList[0].getTransactions().getAmount();
			if(tranAmount.compareTo( new BigDecimal(0)) < 0) {
				smsTranAmount = tranAmount.multiply( new BigDecimal(-1));
			}else {
				smsTranAmount = tranAmount;
			}
			
			BigDecimal presentBalance = new BigDecimal(0);
			BigDecimal smsPresentBalance = new BigDecimal(0);
			presentBalance = ledgerAccountsRepository.findByLedgerAccountsWithBalance(account1.getId());
			
			String balanceType = "";
				if(previousBalanceFirstAccount.compareTo( new BigDecimal(0)) < 0) {
					balanceType = "Cr";
					smsPreviousBalanceFirstAccount = previousBalanceFirstAccount.multiply( new BigDecimal(-1));
				}
				else {
					balanceType = "Dr";
					smsPreviousBalanceFirstAccount = previousBalanceFirstAccount;
				}
			String balanceTypePresentBalance = "";
				if(presentBalance.compareTo( new BigDecimal(0)) < 0) {
					balanceTypePresentBalance = "Cr";
					smsPresentBalance = presentBalance.multiply( new BigDecimal(-1));
				}
				else {
					balanceTypePresentBalance = "Dr";
					smsPresentBalance = presentBalance;
				}

			
			String smsTemplate = "Dear Sir, Your Previous due was "+smsPreviousBalanceFirstAccount+" "+balanceType+", Amount Debited "+smsTranAmount+" Present Balance "+smsPresentBalance+" "+balanceTypePresentBalance+", Thank you!! \n("+companyName+")";
			System.out.println(smsTemplate);
			
			for(LedgerGroup group : account1.getAccountGroup()) {
				if(group.getId() == 2) {
					isInternalAccount = true;
				}
			} // end loop
			if(!isInternalAccount) {
				try {
					System.out.println("Trying to send sms from ledger entries");
					tigerERPSMSSender.SendSMS(fullNumber, smsTemplate);
					tigerERPSMSSender.saveSMSInfo(fullNumber, smsTemplate, account1);
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}else {
				System.out.println("isInternal false . sms not sending for "+account1);
			}
		}
		else {
			System.out.println("first acc not exist");
		}
		if(secondLedger != null) {
			if(secondLedger.isPresent()) {
				LedgerAccounts account2 = secondLedger.get();
				
				if(account2.getAccountAddress().iterator().hasNext()) {
					mobileNum = account2.getAccountAddress().iterator().next().getMobilePhone().getNumber();
					countryCode = "+"+account2.getAccountAddress().iterator().next().getMobilePhone().getCountry().getPhoneCode();
					fullNumber = countryCode.concat(mobileNum);
				}

				System.out.println("Transaction IS: "+ledgerList[0].getTransactions());

				BigDecimal tranAmount = new BigDecimal(0);
				BigDecimal smsTranAmount = new BigDecimal(0);
				BigDecimal smsPreviousBalanceSecondAccount = new BigDecimal(0);
				tranAmount = ledgerList[0].getTransactions().getAmount();
				System.out.println("Transaction Amt IS: "+ledgerList[0].getTransactions().getAmount());
				System.out.println("Tran 2: "+tranAmount);
				if(tranAmount.compareTo( new BigDecimal(0)) < 0) {
					smsTranAmount = tranAmount.multiply( new BigDecimal(-1));
				}
				else {
					smsTranAmount = tranAmount;
				}
				
				BigDecimal presentBalance = new BigDecimal(0);
				BigDecimal smsPresentBalance2 = new BigDecimal(0);
				presentBalance = ledgerAccountsRepository.findByLedgerAccountsWithBalance(account2.getId());
				
				String balanceType = "";
					if(previousBalanceSecondAccount.compareTo( new BigDecimal(0)) < 0) {
						balanceType = "Cr";
						smsPreviousBalanceSecondAccount = previousBalanceSecondAccount.multiply( new BigDecimal(-1));
					}
					else {
						balanceType = "Dr";
						smsPreviousBalanceSecondAccount = previousBalanceFirstAccount;
					}
				String balanceTypePresentBalance = "";
					if(presentBalance.compareTo( new BigDecimal(0)) < 0) {
						balanceTypePresentBalance = "Cr";
						smsPresentBalance2 = presentBalance.multiply( new BigDecimal(-1));
					}
					else {
						balanceTypePresentBalance = "Dr";
						smsPresentBalance2 = presentBalance;
					}

				
				String smsTemplate = "Dear Sir, Your Previous due was "+smsPreviousBalanceSecondAccount+" "+balanceType+", Amount Credited "+smsTranAmount+" Present Balance "+smsPresentBalance2+" "+balanceTypePresentBalance+", Thank you!! \n("+companyName+")";
				System.out.println(smsTemplate);
				
				for(LedgerGroup group : account2.getAccountGroup()) {
					if(group.getId() == 2) {
						isInternalAccountSecond = true;
					}
				}// end loop
				// send sms block start
				if(!isInternalAccountSecond) {
					try {
						System.out.println("Trying to send sms from ledger entries");
						tigerERPSMSSender.SendSMS(fullNumber, smsTemplate);
						tigerERPSMSSender.saveSMSInfo(fullNumber, smsTemplate, account2);
					}catch(Exception ex) {
						ex.printStackTrace();
					}
				}else {
					System.out.println("isInternal false . sms not sending for "+account2);
				}
				
			}
		}
		else {
			System.out.println("sec acc not exist");
		}
		
//		System.out.println(ledgerList);
//		RequestParameter requestParameter = new RequestParameter(); 
//		Page<LedgerEntries> accountTransaction = ledgerEntriesService.findByAccountIdAndCustomQuery((long)2, requestParameter);
//		System.out.println("Param: list Ledger"+ledgerList);
		return ResponseEntity.ok(null);
//		return ResponseEntity.ok(accountTransaction);
	}
	
	@RequestMapping(value="/account", method = RequestMethod.PUT)
	public ResponseEntity<Page<LedgerEntries>> updatingJournalVoucher(@RequestBody LedgerEntries [] ledgerList, HttpSession session) {
		
		Long voucherTypeId = ledgerList[0].getVoucherType().getId();
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if((!accessPermission.getUpdateAllVoucherSettings()) 
				|| (!accessPermission.getUpdateRecepientVoucherSettings() && voucherTypeId== (long)1) 
				|| (!accessPermission.getUpdatePaymentVoucherSettings() && voucherTypeId== (long)2)
				|| (!accessPermission.getUpdateContraVoucherSettings() && voucherTypeId== (long)3) 
				|| (!accessPermission.getUpdateExpenseVoucherSettings() && voucherTypeId== (long)4)
				|| (!accessPermission.getUpdateJournalVoucherSettings() && voucherTypeId== (long)5)) {
			return ResponseEntity.badRequest().build();
		}
		
		System.out.println("PUT Method");
		User user  = userService.findUserByEmail((String) session.getAttribute("userName"));
		LedgerEntries ledgerEntries[] = new LedgerEntries[2];
		for(int i=0; i<ledgerList.length; i++) {
			ledgerEntries[i]=ledgerList[i];
			ledgerEntries[i].setCreatedBy(user);
			System.out.println("\nentries id: "+ledgerList[i].getId()+"  || entries  transaction id: "+ledgerList[i].getTransactions().getId()+"\n");
		}
//		transactionService.makeDoubleEntryTransaction(ledgerEntries);//to change
//		System.out.println(ledgerList);
//		RequestParameter requestParameter = new RequestParameter(); 
//		Page<LedgerEntries> accountTransaction = ledgerEntriesService.findByAccountIdAndCustomQuery((long)2, requestParameter);
//		System.out.println("Param: list Ledger"+ledgerList);
		return ResponseEntity.ok(null);
//		return ResponseEntity.ok(accountTransaction);
	}

}

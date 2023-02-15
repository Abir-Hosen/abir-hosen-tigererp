package com.tigerslab.tigererp.config.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.Role;
import com.tigerslab.tigererp.model.User;
import com.tigerslab.tigererp.model.financial.AccountChart;
import com.tigerslab.tigererp.model.financial.AccountEquation;
import com.tigerslab.tigererp.model.financial.LedgerAccounts;
import com.tigerslab.tigererp.model.financial.LedgerGroup;
import com.tigerslab.tigererp.model.financial.TransactionEntryType;
import com.tigerslab.tigererp.model.financial.VoucherType;
import com.tigerslab.tigererp.model.init.ApplicationInfo;
import com.tigerslab.tigererp.model.inventory.ProductTransactionEntryType;
import com.tigerslab.tigererp.model.org.Branch;
import com.tigerslab.tigererp.model.org.PaperType;
import com.tigerslab.tigererp.model.user.Gender;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.model.user.employee.Employee;
import com.tigerslab.tigererp.model.user.employee.EmployeeCategory;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;
import com.tigerslab.tigererp.repository.init.InitRepository;
import com.tigerslab.tigererp.repository.user.RoleRepository;
import com.tigerslab.tigererp.service.financial.AccountChartService;
import com.tigerslab.tigererp.service.financial.AccountEquationService;
import com.tigerslab.tigererp.service.financial.LedgerAccountsService;
import com.tigerslab.tigererp.service.financial.TransactionEntryTypeService;
import com.tigerslab.tigererp.service.financial.VoucherTypeService;
import com.tigerslab.tigererp.service.init.InitService;
import com.tigerslab.tigererp.service.org.BranchService;
import com.tigerslab.tigererp.service.org.LedgerGroupService;
import com.tigerslab.tigererp.service.org.PaperTypeService;
import com.tigerslab.tigererp.service.org.inventory.ProductTransactionEntryTypeService;
import com.tigerslab.tigererp.service.user.GenderService;
import com.tigerslab.tigererp.service.user.UserService;
import com.tigerslab.tigererp.service.user.employee.AccessPermissionService;
import com.tigerslab.tigererp.service.user.employee.EmployeeCategoryService;
import com.tigerslab.tigererp.service.user.employee.EmployeeRoleService;

@Component
public class InitializeConfig implements ApplicationRunner {
	
	@Autowired
	private InitService initService;
	
	@Autowired
	private InitRepository initRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmployeeRoleService employeeRoleService;
	
	@Autowired
	private EmployeeCategoryService empCategoryService;
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private AccountEquationService accountEquationService;
	
	@Autowired
	private AccountChartService accountChartService;
	
	@Autowired
	private LedgerGroupService ledgerGroupService;
	
	@Autowired
	private GenderService genderService;
	
	@Autowired
	private LedgerAccountsService ledgerAccountsService;
	
	@Autowired
	private TransactionEntryTypeService transactionEntryTypeService;
	
	@Autowired
	private VoucherTypeService voucherTypeService;
	
	@Autowired
	private ProductTransactionEntryTypeService productTransactionEntryTypeService;
	
	@Autowired
	private PaperTypeService paperTypeService;
	
	@Autowired
	private AccessPermissionService accessPermissionService;
	
	public InitializeConfig() {
	}
	
	public boolean isInstalledSqlTable() {
		boolean isInstall = false;
		ApplicationInfo appInfo = null;
		Pageable pageable = PageRequest.of(0, 1000);
		if(initRepository.findAllBySortAndOrder(pageable).getContent().iterator().hasNext()) {
			appInfo = (ApplicationInfo)initRepository.findAllBySortAndOrder(pageable).getContent().iterator().next();
		}
		
		//System.out.println(initRepository.findAllBySortAndOrder(pageable).getContent().iterator().next());
		if(appInfo != null) {
			isInstall = appInfo.getIsInstalled();
		}
		
		return isInstall;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("RUNRUNNER");
		
		//if you initialize VoucherType() after first run, then remove comment for the following line! 
		//then re comment or remove it!
		//initializeVoucherType();
		
		if(isInstalledSqlTable()) {
		}
		else {
			initializeUserRole();
			initOtherDataTable();
			saveApplicationInfo();
			System.out.println("Application Initialize completed");
		}
	}
	
	
	public void initializeUserRole() {
		
		roleRepository.save(new Role(Long.valueOf(1), ConstantFactory.ROLE_CUSTOMER));
		roleRepository.save(new Role(Long.valueOf(2), ConstantFactory.ROLE_EMPLOYEE));
		roleRepository.save(new Role(Long.valueOf(3), ConstantFactory.ROLE_ADMIN));
	}
	
	
	
	public void saveApplicationInfo() {
		initService.save(new ApplicationInfo(Long.valueOf(1), true));
	}
	
	public void initOtherDataTable() {
		// Create default Employee Category
		EmployeeCategory empCategory = new EmployeeCategory();
		empCategory.setCategoryName(ConstantFactory.PRIMARY);
		empCategory.setAlius(ConstantFactory.PRIMARY);
		//empCategory.setIsDeletable(false);
		empCategory.setCategoryDescription(ConstantFactory.DEFAULT_VALUE);
		empCategory = empCategoryService.save(empCategory);
		
		// create EmployeeRole
		EmployeeRole empRole = new EmployeeRole();
		empRole.setId(1);
		empRole.setRoleName(ConstantFactory.ROLE_MANAGING_DIRECTOR);
		empRole.setAlius(ConstantFactory.ROLE_MANAGING_DIRECTOR);
		empRole.setRoleDescription(ConstantFactory.ROLE_DESCRIPTION);
		empRole = employeeRoleService.save(empRole);
		
		//Create Access Permission
		AccessPermission accessPermission = new AccessPermission();
		accessPermission.setId((long) 1);
		accessPermission.setReadRole(true);
		accessPermission.setReadAccessPermissionSettings(true);
		accessPermission.setCreateAccessPermissionSettings(true);
		accessPermission.setUpdateAccessPermissionSettings(true);
		accessPermission.setEmployeeRole(employeeRoleService.findById((long) 1).get());
		accessPermissionService.save(accessPermission);
		
		// create default branch
		Branch branch = new Branch();
		branch.setBranchName(ConstantFactory.BRANCH_MAIN);
		branch.setAlius(ConstantFactory.BRANCH_MAIN);
		branch.setDescription(ConstantFactory.DEFAULT_VALUE);
		branch = branchService.save(branch);
		
		// Create Gender
		Gender genderMale = new Gender();
		genderMale.setName(ConstantFactory.MALE);
		genderMale.setDescription(ConstantFactory.DEFAULT_VALUE);
		genderMale = genderService.save(genderMale);
		
		Gender genderFemaleMale = new Gender();
		genderFemaleMale.setName(ConstantFactory.FEMALE);
		genderFemaleMale.setDescription(ConstantFactory.DEFAULT_VALUE);
		genderFemaleMale = genderService.save(genderFemaleMale);
		
		//create Employee
		Employee employee = new Employee();
		employee.setBranch(new HashSet<Branch>(Arrays.asList(branch)));
		
		User user = new User();
		user.setActive(1);
		user.setFirstName("Admin User First Name");
		user.setLastName("Admin User Last Name");
		user.setEmail(ConstantFactory.ADMIN_USER_NAME);
		user.setPassword(ConstantFactory.ADMIN_PASSWORD);
		user.setEmpRoles(new HashSet<EmployeeRole>(Arrays.asList(empRole)));
		user.setEmployee(employee);
		user = userService.saveUser(user, ConstantFactory.ROLE_EMPLOYEE);
		
		//Create ledger Group
		LedgerGroup ledgerGroup = new LedgerGroup();
		ledgerGroup.setName(ConstantFactory.PRIMARY);
		ledgerGroup.setAlias(ConstantFactory.PRIMARY);
		ledgerGroup.setDescription(ConstantFactory.DEFAULT_VALUE);
		ledgerGroup = ledgerGroupService.save(ledgerGroup);
		
		LedgerGroup ledgerGroupInternal = new LedgerGroup();
		ledgerGroupInternal.setName(ConstantFactory.INTERNAL);
		ledgerGroupInternal.setAlias(ConstantFactory.INTERNAL);
		ledgerGroupInternal.setDescription(ConstantFactory.DEFAULT_VALUE);
		ledgerGroupInternal = ledgerGroupService.save(ledgerGroupInternal);
		
		//Initialize Voucher type
		voucherTypeService.save(new VoucherType(Long.valueOf(1), "Receipt Vhoucher", "Receipt Vhoucher description!"));
		voucherTypeService.save(new VoucherType(Long.valueOf(2), "Payment Voucher", "Payment Voucher description!"));
		voucherTypeService.save(new VoucherType(Long.valueOf(3), "Contra Voucher", "Contra Voucher description!"));
		voucherTypeService.save(new VoucherType(Long.valueOf(4), "Expense Voucher", "Expense Voucher description!"));
		voucherTypeService.save(new VoucherType(Long.valueOf(5), "Journal Voucher", "Journal Voucher description!"));
		
		//Initialize Invoice Paper Type
		paperTypeService.save(new PaperType(Long.valueOf(1), "Letter", 8.5f, 11f, "Size: Letter(US), Milimeters: 215.9 x 279.4, Inch: 8.5 x 11"));
		paperTypeService.save(new PaperType(Long.valueOf(2), "Legal", 8.5f, 14f, "Size: Legal(US), Milimeters: 215.9 x 355.6, Inch: 8.5 x 14"));
		paperTypeService.save(new PaperType(Long.valueOf(3), "Ledger", 11f, 17f, "Size: Ledger(US), Milimeters: 279.4 x 431.8, Inch: 11 X 17"));
		paperTypeService.save(new PaperType(Long.valueOf(4), "A-0", 33.125f, 46.75f, "Size: A0, Milimeters: 841 x 1189, Inch: 33.125 x 46.75"));
		paperTypeService.save(new PaperType(Long.valueOf(5), "A-1", 23.375f, 33.125f, "Size: A1, Milimeters: 594 x 841, Inch: 23.375 x 33.125"));
		paperTypeService.save(new PaperType(Long.valueOf(6), "A-2", 16.5f, 23.375f, "Size: A2, Milimeters: 420 x 594, Inch: 16.5 x 23.375"));
		paperTypeService.save(new PaperType(Long.valueOf(7), "A-3", 11.75f, 16.5f, "Size: A3, Milimeters: 297 x 420, Inch: 11.75 x 16.5"));
		paperTypeService.save(new PaperType(Long.valueOf(8), "A-4", 8.25f, 11.75f, "Size: A4, Milimeters: 210 x 297, Inch:  8.25 x11.75"));
		paperTypeService.save(new PaperType(Long.valueOf(9), "A-5", 5.8f, 8.3f, "Size: A5, Milimeters: willput x willput, Inch:  8.25 x11.75"));
		paperTypeService.save(new PaperType(Long.valueOf(10), "Thermal Paper", 6f, 10f, "Size: Thermal paper, Milimeters: 152.4 x 254, Inch:  6 x10"));
		
		// Initialize product transaction types
		productTransactionEntryTypeService.save(new ProductTransactionEntryType(Long.valueOf(1), ConstantFactory.PURCHASE, "+"));
		productTransactionEntryTypeService.save(new ProductTransactionEntryType(Long.valueOf(2), ConstantFactory.SALE, "-"));
		productTransactionEntryTypeService.save(new ProductTransactionEntryType(Long.valueOf(3), ConstantFactory.STOCK_IN, "+"));
		productTransactionEntryTypeService.save(new ProductTransactionEntryType(Long.valueOf(4), ConstantFactory.STOCK_OUT, "-"));
		productTransactionEntryTypeService.save(new ProductTransactionEntryType(Long.valueOf(5), ConstantFactory.CANCEL_PURCHASE, "-"));
		productTransactionEntryTypeService.save(new ProductTransactionEntryType(Long.valueOf(6), ConstantFactory.CANCEL_SALE, "+"));
		productTransactionEntryTypeService.save(new ProductTransactionEntryType(Long.valueOf(7), ConstantFactory.PRODUCTION, "+"));
		productTransactionEntryTypeService.save(new ProductTransactionEntryType(Long.valueOf(8), ConstantFactory.CONSUMPTION, "-"));
		
		// Create transactionEntryType
		TransactionEntryType debitEntry = new TransactionEntryType();
		debitEntry.setName(ConstantFactory.DEBIT);
		debitEntry = transactionEntryTypeService.save(debitEntry);
		
		TransactionEntryType creditEntry = new TransactionEntryType();
		creditEntry.setName(ConstantFactory.CREDIT);
		creditEntry = transactionEntryTypeService.save(creditEntry);
		
		// Create Accounting Basic Equation
		AccountEquation accountEquationAsset = new AccountEquation();
		accountEquationAsset.setName(ConstantFactory.ASSETS);
		accountEquationAsset.setAlius(ConstantFactory.ASSETS);
		accountEquationAsset.setDescription(ConstantFactory.DEFAULT_VALUE);
		accountEquationAsset = accountEquationService.save(accountEquationAsset);
		
		AccountEquation accountEquatioLiabilities = new AccountEquation();
		accountEquatioLiabilities.setName(ConstantFactory.LIABILITIES);
		accountEquatioLiabilities.setAlius(ConstantFactory.LIABILITIES);
		accountEquatioLiabilities.setDescription(ConstantFactory.DEFAULT_VALUE);
		accountEquatioLiabilities = accountEquationService.save(accountEquatioLiabilities);
		
		AccountEquation accountEquatioEquity = new AccountEquation();
		accountEquatioEquity.setName(ConstantFactory.EQUITY);
		accountEquatioEquity.setAlius(ConstantFactory.EQUITY);
		accountEquatioEquity.setDescription(ConstantFactory.DEFAULT_VALUE);
		accountEquatioEquity = accountEquationService.save(accountEquatioEquity);
		
		AccountEquation accountEquatioRevenue = new AccountEquation();
		accountEquatioRevenue.setName(ConstantFactory.REVENUE);
		accountEquatioRevenue.setAlius(ConstantFactory.REVENUE);
		accountEquatioRevenue.setDescription(ConstantFactory.DEFAULT_VALUE);
		accountEquatioRevenue = accountEquationService.save(accountEquatioRevenue);
		
		AccountEquation accountEquatioExpenses = new AccountEquation();
		accountEquatioExpenses.setName(ConstantFactory.EXPENSES);
		accountEquatioExpenses.setAlius(ConstantFactory.EXPENSES);
		accountEquatioExpenses.setDescription(ConstantFactory.DEFAULT_VALUE);
		accountEquatioExpenses = accountEquationService.save(accountEquatioExpenses);
		
		// Create ChartOfAccounting
		AccountChart accountChartCash = new AccountChart();
		accountChartCash.setName(ConstantFactory.CASH);
		accountChartCash.setDescription(ConstantFactory.DEFAULT_VALUE);
		accountChartCash.setParentAccount(accountEquationAsset);
		accountChartCash = accountChartService.save(accountChartCash);
		
		AccountChart accountChartCashInBank = new AccountChart();
		accountChartCashInBank.setName(ConstantFactory.CASH_IN_BANK);
		accountChartCashInBank.setDescription(ConstantFactory.DEFAULT_VALUE);
		accountChartCashInBank.setParentAccount(accountEquationAsset);
		accountChartCashInBank = accountChartService.save(accountChartCashInBank);
		
		AccountChart accountChartAccountReceivable = new AccountChart();
		accountChartAccountReceivable.setName(ConstantFactory.ACCOUNTS_RECEIVABLE);
		accountChartAccountReceivable.setDescription(ConstantFactory.DEFAULT_VALUE);
		accountChartAccountReceivable.setParentAccount(accountEquationAsset);
		accountChartAccountReceivable = accountChartService.save(accountChartAccountReceivable);
		
		AccountChart accountChartPurchase = new AccountChart();
		accountChartPurchase.setName(ConstantFactory.PURCHASE);
		accountChartPurchase.setDescription(ConstantFactory.DEFAULT_VALUE);
		accountChartPurchase.setParentAccount(accountEquationAsset);
		accountChartPurchase = accountChartService.save(accountChartPurchase);
		
		AccountChart accountChartSale = new AccountChart();
		accountChartSale.setName(ConstantFactory.REVENUE);
		accountChartSale.setDescription(ConstantFactory.DEFAULT_VALUE);
		accountChartSale.setParentAccount(accountEquatioRevenue);
		accountChartSale = accountChartService.save(accountChartSale);
		
		AccountChart accountChartExpense = new AccountChart();
		accountChartExpense.setName(ConstantFactory.EXPENSES);
		accountChartExpense.setDescription(ConstantFactory.DEFAULT_VALUE);
		accountChartExpense.setParentAccount(accountEquatioExpenses);
		accountChartExpense = accountChartService.save(accountChartExpense);
		
		AccountChart accountChartOwnersEquity = new AccountChart();
		accountChartOwnersEquity.setName(ConstantFactory.OWNERS_EQUITY);
		accountChartOwnersEquity.setDescription(ConstantFactory.DEFAULT_VALUE);
		accountChartOwnersEquity.setParentAccount(accountEquatioEquity);
		accountChartOwnersEquity = accountChartService.save(accountChartOwnersEquity);
		
		AccountChart accountChartCapital = new AccountChart();
		accountChartCapital.setName(ConstantFactory.CAPITAL);
		accountChartCapital.setDescription(ConstantFactory.DEFAULT_VALUE);
		accountChartCapital.setParentAccount(accountEquationAsset);
		accountChartCapital = accountChartService.save(accountChartCapital);
		
//		AccountChart accountChartAccountInventory = new AccountChart();
//		accountChartAccountInventory.setName(ConstantFactory.INVENTORY);
//		accountChartAccountInventory.setDescription(ConstantFactory.DEFAULT_VALUE);
//		accountChartAccountInventory.setParentAccount(accountEquationAsset);
//		accountChartAccountInventory = accountChartService.save(accountChartAccountInventory);
		
		AccountChart accountChartAccountPayable = new AccountChart();
		accountChartAccountPayable.setName(ConstantFactory.ACCOUNTS_PAYABLE);
		accountChartAccountPayable.setDescription(ConstantFactory.DEFAULT_VALUE);
		accountChartAccountPayable.setParentAccount(accountEquatioLiabilities);
		accountChartAccountPayable = accountChartService.save(accountChartAccountPayable);
		
		AccountChart accountChartAccountLiabilities = new AccountChart();
		accountChartAccountLiabilities.setName(ConstantFactory.LIABILITIES);
		accountChartAccountLiabilities.setDescription(ConstantFactory.DEFAULT_VALUE);
		accountChartAccountLiabilities.setParentAccount(accountEquatioLiabilities);
		accountChartAccountLiabilities = accountChartService.save(accountChartAccountLiabilities);
		
		// Create new default Ledger
		LedgerAccounts purchaseAccount = new LedgerAccounts();
		purchaseAccount.setAccountGroup( new ArrayList<LedgerGroup>(Arrays.asList(ledgerGroupInternal)));
		purchaseAccount.setAccountName(ConstantFactory.PURCHASE);
		purchaseAccount.setAlius(ConstantFactory.PURCHASE);
		purchaseAccount.setParentAccount(accountChartPurchase);
		purchaseAccount = ledgerAccountsService.save(purchaseAccount);
		
		LedgerAccounts cashAccount = new LedgerAccounts();
		cashAccount.setAccountGroup( new ArrayList<LedgerGroup>(Arrays.asList(ledgerGroupInternal)));
		cashAccount.setAccountName(ConstantFactory.CASH);
		cashAccount.setAlius(ConstantFactory.CASH);
		cashAccount.setParentAccount(accountChartCash);
		cashAccount = ledgerAccountsService.save(cashAccount);
		
		LedgerAccounts saleAccount = new LedgerAccounts();
		saleAccount.setAccountGroup( new ArrayList<LedgerGroup>(Arrays.asList(ledgerGroupInternal)));
		saleAccount.setAccountName(ConstantFactory.SALE);
		saleAccount.setAlius(ConstantFactory.SALE);
		saleAccount.setParentAccount(accountChartSale);
		saleAccount = ledgerAccountsService.save(saleAccount);
		
		LedgerAccounts expensesAccount = new LedgerAccounts();
		expensesAccount.setAccountGroup( new ArrayList<LedgerGroup>(Arrays.asList(ledgerGroupInternal)));
		expensesAccount.setAccountName(ConstantFactory.EXPENSES);
		expensesAccount.setAlius(ConstantFactory.EXPENSES);
		expensesAccount.setParentAccount(accountChartExpense);
		expensesAccount = ledgerAccountsService.save(expensesAccount);
		
		LedgerAccounts capitalAccount = new LedgerAccounts();
		capitalAccount.setAccountGroup( new ArrayList<LedgerGroup>(Arrays.asList(ledgerGroupInternal)));
		capitalAccount.setAccountName(ConstantFactory.CAPITAL);
		capitalAccount.setAlius(ConstantFactory.CAPITAL);
		capitalAccount.setParentAccount(accountChartCapital);
		capitalAccount = ledgerAccountsService.save(capitalAccount);
		
		
	}
	

}

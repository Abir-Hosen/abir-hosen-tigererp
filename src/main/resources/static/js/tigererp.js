var app = angular.module('EmployeeApp', [ 'ngMaterial', 'ngMessages',
		'ui.router', 'md.data.table', 'fixed.table.header', 'ngResource',
		'pascalprecht.translate', 'chart.js' ]);

app.factory('$apiurl', [ '$resource', function($resource) {
	'use strict';
	return {
		user : $resource('api/user/:type/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		accountchart : $resource('api/accountchart/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		accountequation: $resource('api/accountequation'),
		accounttransaction: $resource('api/ledgerentries/:type/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		branches : $resource('api/branch/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		employeeRoles : $resource('api/employeeroles/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		accessPermission : $resource('api/accesspermission/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		employee : $resource('api/employee/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		employeeCategories : $resource('api/employeecategories/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		stockCategories : $resource('api/stockcategory/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		stockItems : $resource('api/stockItem/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		genders : $resource('api/gender'),
		country : $resource('api/country/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		company : $resource('api/company/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		document : $resource('api/document/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		ledger : $resource('api/ledgerAccounts/:balance/:id', {id : '@id'}, {update : {method : 'PUT'}}),//To complete url;
		ledgergroups : $resource('api/ledgergroup/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		units : $resource('api/units/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		subjectQuery : $resource('api/subjects/query'),
		roles : $resource('api/roles/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		room : $resource('api/room/:branch/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		privileges : $resource('api/privileges/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		pteType : $resource('api/productTransactionEntryType/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		order : $resource('api/order/:by/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		paperType : $resource('api/paperType/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		voucherType : $resource('api/voucherType/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		sms : $resource('api/sendsms/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		profitloss : $resource('api/profitloss/:type/:id', {id : '@id'}, {update : {method : 'PUT'}}),
		dashboard: $resource('api/dashboard/:type/:id', {id : '@id'}, {update : {method : 'PUT'}})
	};
}]);

app.config([
		'$mdThemingProvider',
		'$qProvider',
		function($mdThemingProvider, $qProvider) {
			'use strict';
			$qProvider.errorOnUnhandledRejections(false);

			$mdThemingProvider.theme('default').primaryPalette('blue')
					.accentPalette('blue');
			;
		} ]);

app.config(function($translateProvider) {
	$translateProvider.translations('en', {
		ACCESS_PERMISSION : 'Access Permission',	
		ACCOUNTS : 'Accounts',	
		ACCOUNT_CHART : 'Account Chart',	
		ACCOUNT_CHART : 'Account Chart',	
		ACCOUNT_CHART_NAME : 'Account Chart Name',
		ACCOUNT_CHART_SETTINGS : 'Account Chart Settings',	
		ACCOUNT_EQUATION: 'Account Equation',	
		ACCOUNT_INFORMATION : 'Account Information',	
		ACTION : 'Action',	
		ADD : 'Add',
		ADDRESS : 'Address',
		ADDRESS_LINE_1 : 'Address Line 1',	
		ADDRESS_LINE_2 : 'Address Line 2',	
		ADD_ACCOUNT_CHART : 'Add Account Chart',
		ADD_BRANCH : 'Add New Branch',	
		ADD_EMPLOYEE : 'ADD EMPLOYEE',
		ADD_ITEM : 'Add item',	
		ADD_JOURNAL_VOUCHER : 'Add Journal Voucher',
		ADD_VOUCHER : 'Add Voucher Voucher',
		ADD_UNIT : 'Add New Unit',	
		ADD_PURCHASE_ORDER: 'Add Purchase Order',	
		ADD_PURCHASE_VOUCHER : 'Add Purchase Voucher',	
		ADD_SALE_ORDER : 'Add Sale Order',
		ADD_SALE_VOUCHER : 'Add Sale Voucher',	
		ADD_SPACE_SYMBOL_AMMOUNT : 'Add space between symbol and amount',
		ADD_STOCK_IN : 'Add Stock In',
		ADD_STOCK_ITEM : 'Add Stock Item',
		ADD_VOUCHER : 'Add Voucher',	
		ALIUS : 'Alius',	
		ALL_FIELDS_ARE_REQUIRED : 'All fields are required.',	
		ALL_ORDER : 'All Order',	
		ALL_VOUCHER : 'All Voucher',
		ALT_MOBILE : 'Alt. Mobile',
		AMOUNT : 'Amount',	
		APPROVED_BY : 'Approved By',	
		AVAILABLE_QUANTITY : 'Available Quantity',	
		BALANCE_SHEET : 'Balance Sheet',	
		BASIC_CURRENCY_SYMBOL : 'Basic Currency Symbol',
		BASIC_INFORMATION : 'Basic Information',
		BODY : 'Body',
		BRANCH : 'Branch',
		BRANCH_ADDRESS : 'Branch Address',
		BRANCH_DESCRIPTION : 'Branch Description',
		BRANCH_NAME : 'Branch Name',
		BRANCH_SETTINGS : 'Branch Settings',	
		BUTTON_LANG_DE : 'german',	
		BUTTON_LANG_EN : 'english',	
		CANCEL : 'CANCEL',	CANCEL_ON : 'Cancel On',	
		CANCEL_PURCHASE_ORDER : 'Cancel Purchase Order',	
		CANCEL_SALE_ORDER : 'Cancel Sale Order',
		CATEGORY : 'Category',
		CHANGE_PASSWORD : 'Change Password',	
		CHART_OF_ACCOUNTING : 'Chart of Accounting',	
		CHECK_TOTAL : 'SubTotal',	
		CITY : 'City',	CLOSE : 'Close',	
		CLOSE_THE_SIDES : 'Close the sides',
		CODE : 'code',
		COMPANY_BRANCHES : 'Company Branches',	
		COMPANY_ID : 'Company Id',
		COMPANY_LOGO : 'Company Logo',
		COMPANY_NAME : 'Company Name',	
		COMPANY_SETTINGS : 'Company Settings',	
		COMPOUND : 'Compount',	
		CONFIRM : 'Confirm',	
		CONSUMPTION : 'Consumption',	
		CONFIRM: 'Confirm new password',	
		CONTACT_INFORMATION : 'Contact Information',
		CONTACT_PERSON_NAME : 'Contact Person Name',	
		CONTRA_VOUCHER : 'Contra Voucher',	
		COUNTRY : 'Country',
		COST_OF_GOODS_SOLD : 'Cost of goods sold',
		CREATE : 'Create',
		CREATED_BY : 'Created By',	
		CREATE_STOCK_CATEGORY : 'Create Stock Category',	
		CREATE_STOCK_CATEGORY : 'Create Stock Category',
		CREATION_DATE : 'Creation date',
		CREDIT : 'Credit',
		CREDIT_ACCOUNT : 'Credit Account',	
		CREDIT_ACCOUNT : 'Credit Account',	
		CURRENT_ACCOUNT_BALANCE : 'Current Account Balance',
		DATE : 'Date',	
		DATE_OF_BIRTH : 'Date of Birth',
		DEBIT : 'Debit',	
		DEBIT_ACCOUNT : 'Debit Account',
		DEBIT_ACCOUNT : 'Debit Account',	
		DECIMEL_PLACES : 'Decimel Places',
		DELETE : 'Delete',
		DELIVERY : 'Delivery',
		DESCRIPTION : 'Description',
		DISC : 'Disc(%)',	
		DUE_AMOUNT : 'Due Amount',
		EDIT : 'Edit',	
		EDIT_BRANCH : 'Edit Branch',	
		EDIT_ROLE : 'Edit Role',	
		EMAIL : 'Email',	
		EMPLOYEE : 'Employee',
		EMPLOYEE_ACCESS_PERMISSION : 'Employee access permissions',
		EMPLOYEE_CATEGORY : 'Employee Category',	
		EMPLOYEE_ROLE : 'Employee Role',
		EMPLOYEE_SETTINGS : 'Employee Settings',
		ERASE : 'Erase',
		EXP : 'exp',
		EXPENSES : 'Expenses',
		EXPENSE_VOUCHER : 'Expense Voucher',
		FACEBOOK : 'Facebook',
		FATHER_NAME : 'Father Name',
		FAX : 'Fax',
		FINANCIAL_YEAR : 'Financial Year Starts From',	
		FIRST_NAME : 'First Name',
		FIRST_PARTY : 'First Party',	
		FIRST_UNIT : 'First Unit',
		FOO : 'This is a paragraph.',
		FORMAL_CURRENCY_NAME : 'Formal Currency Name',
		FORMAL_NAME : 'Formal Name',	
		FORMAL_NAME : 'Formal Name',
		FORMAL_NAME : 'Formal Name',
		FOUNDATION_DATE : 'Foundation Date',	
		GENDER : 'Gender',
		GODOWN_AND_LOT : 'Godown & Lot',
		GRAND_TOTAL : 'Grand Total',
		GROSS_PROFIT : 'Gross Profit',
		HR_PAYROLE : 'HR/Payrole',	
		ID : 'Id',
		IMAGE_AND_LOGO_SETTINGS : 'Image and logo',
		INDEX : 'Index',
		INVENTORY : 'Inventory',
		INVOICE: 'Invoice',	
		INCOME: 'Income Statement',	
		INVOICE_PAPER_TYPE : 'Invoice Paper Type',
		ITEM : 'Item',	
		ITEM_NAME : 'Item Name',	
		JOINING_DATE : 'Joining Date',
		JOURNAL_VOUCHER : 'Journal Voucher',	
		JOURNAL_VOUCHER : 'Journal Voucher',	
		LAST_NAME : 'Last Name',
		LEDGER : 'Ledger',
		LEDGER_ENTRIES : 'Ledger Entries',	
		LEDGER_ENTRIES_REFERENCE : 'L.E. ref',
		LEDGER_GROUP : 'Ledger Group',
		LEDGER_GROUP_SETTINGS : 'Ledger Group',	
		LEDGER_INFORMATION : 'Ledger Information',
		LESS_DISCOUNT : 'Less Discount',
		LOCKED_BY : 'Locked By',	
		LOGOUT : 'Logout',
		MAINTAIN_BRANCH : 'Maintain Batch',	
		MENUFACTURE : 'Manufacture',	
		MEDIA_AND_DOCUMENT : 'Media and documents',	
		MFG : 'mfg',	
		MINIMUM_SELLING_PRICE : 'Minimum Selling price',
		MOBILE : 'Mobile',	
		MOTHER_NAME : 'Mother Name',
		NAME : 'Name',
		NAME_OF_ACCOUNT_CHART : 'Name of account chart',
		NARATION : 'Naratuion',
		NEW : 'New',	
		NET_PROFIT : 'Net Profit',	
		NID : 'Nid No',
		NO : 'No',	
		NO_DECIMAL_PLACES : 'No. of decimal places',
		NUMBER : 'Number',
		OF : 'Of',
		OPENING_BALANCE : 'Opening Balance',
		ORDER : 'Order',	
		ORDER_DATE : 'Order Date',
		ORDER_STOCK_ITEM : 'Order Stock Item',
		OTHER_SETTINGS : 'Other Settings',
		PAID_AMOUNT : 'Paid Amount',	
		PARENT : 'Parent',
		PARENT_ACCOUNT : 'Parent account',	
		PARTICULERS : 'Particulars',	
		PARTY_ACCOUNT_NAME : 'Party Account Name',	
		PASSWORD : 'Password',		
		PASSWORD_EMAIL_RESET: 'Reset Email-Password',	
		PASSWORD_CONFIRM : 'Password Confirm',	
		PAYMENT : 'Payment',
		PAYMENT_VOUCHER : 'Payment Voucher',	
		PERMISSION_SET : 'Set Permission',
		PHONE : 'Phone',	
		PRODUCTION : 'Production',	
		PROFIT_LOSS_STATEMENT : 'Profit and Loss Statement',	
		PREVIOUS_BALANCE : 'Previous Balance',
		PROPREITOR_NAME : 'Proprietor Name',	
		PURCHASE : 'Purchase',
		PURCHASED : 'Purchased',	
		PURCHASE_CANCELATION : 'Purchase Cancelation',	
		PURCHASE_LEDGER : 'Purchase Ledger',
		PURCHASE_ORDER : 'Purchase Order',
		PURCHASE_ORDER : 'Purchase Order',
		QTY : 'Qty',
		QUANTITY : 'Quantity',	
		RATE : 'Rate',
		READ : 'Read',	
		RECEPIENT_VOUCHER : 'Recepient Voucher',
		REG : 'Reg. No.',
		REORDER_QUANTITY : 'Reorder Quantity',
		REPORTS : 'Reports',
		REPORT_HEADER : 'Report Header',	
		REVART : 'Revart',
		ROLE : 'Role',
		ROOM : 'Room',
		ROOM_NO : 'Room No',
		ROOM_SETTINGS : 'Room Settings',
		SALE : 'Sale',	
		SALE_CANCELATION : 'Sale Cancelation',
		SALE_LEDGER : 'Sale Ledger',	
		SALE_LEDGER : 'Sale Ledger',	
		SALE_ONLINE : 'Sale Online',	
		SALE_ORDER : 'Sale Order',	
		SALE_ORDER : 'Sale Order',	
		SAVE : 'Save',	SAVE_ROLE : 'Save Role',
		SEARCH : 'Search by Name/ID',
		SECOND_PARTY : 'Second Party',	
		SECOND_UNIT : 'Second Unit',	
		SELECT_ACCOUNT_EQUATION : 'Select Account Equation',	
		SELECT_CATEGORY : 'Select Category',
		SELECT_COUNTRY : 'Select a Country',	
		SELECT_CREDIT_ACCOUNT : 'Select credit account',
		SELECT_CUSTOM_TRANSACTION_DATE : 'Select Another Transaction Date',	
		SELECT_DATE : 'Select Date',
		SELECT_DEBIT_ACCOUNT : 'Select Debit account',
		SELECT_ROLE : 'Select Role',
		SELECT_UNIT_TYPE : 'Select Unit Type',	
		SELLING_PRICE : 'Selling Price',
		SELLING_PRICE : 'Selling Price',	
		SETTINGS : 'Setting',	
		SET_CREDIT_LIMIT : 'Set Credit limit',
		SHOW_AMOUNT_IN_MILLION : 'Show ammount in million',	
		SMS_LOG : 'SMS',
		SPOUSE_NAME : 'Spouse Name',
		STATUS : 'Status',
		STOCK_CATEGORY : 'Stock Category',	
		STOCK_CATEGORY : 'Stock Category',
		STOCK_IN : 'Stock In',	
		STOCK_ITEM : 'Stock Item',	
		STOCK_OUT : 'Stock Out',	
		STOCK_RECORD_BOOK : 'Stock Record Book',	
		SUBMIT : 'SUBMIT',	
		SUB_TOTAL : 'Sub Total',	
		SUPPLIER_INVOICE_NUMBER : 'Supplier Invoice Number',	
		SURE_DELETE : 'Are you sure want to delete?',
		SURE_REVART : 'Are you sure want to revart?',
		SYMBOL_SUFFIX_TO_AMOUNT : 'Symbol suffix to amount',	
		THIS_FIELD_IS_REQUIRED : 'This field is required',
		TIGER_ERP_MENU : 'TIGER ERP MENU',	
		TITLE : 'Title',	
		TOTAL : 'Total',
		TOTAL_AMOUNT : 'Total Amount',
		TOTAL_SALE_AMOUNT : 'Total Sale Amount',
		TOTAL_CREDIT : 'Total Credit',	
		TOTAL_DEBIT : 'Total Debit',	
		TRANSACTIONS : 'Transactions',
		UNIT : 'Unit',
		UNIT_OF_MEASUREMENY : 'Unit of Measurement',	
		UNIT_PRICE: 'Unit Price',	
		UNIT_SYMBLE : 'Unit Symble',	
		UPDATE : 'UPDATE',
		UPDATE_ACCOUNT_CHART : 'Update Account Chart',	
		UPDATE_ITEM : 'Update Item',	
		UPDATE_JOURNAL_VOUCHER : 'Update Journal voucher',	
		UPDATE_VOUCHER : 'Update Voucher',	
		UPDATE_STOCK_CATEGORY: 'Update Stock Category',
		UPDATE_STOCK_ITEM : 'Update Stock Item',
		UPDATE_UNIT : 'Update Unit',		
		UPLOAD_FILE : 'Upload File',
		USER_NAME : 'User Name',
		VAT : 'Vat No.',	
		VIEW_ORDER : 'View Order',	
		VOUCHER : 'Voucher',
		VOUCHER_TYPE : 'Voucher Type',
		WEBSITE : 'Website',
		WHATS_APP : 'Whats app',	
		WORD_DEFINE_OF_DECIMAL_PLACES : 'Word define of Decimal Places:',	
		ZIP : 'Zip',
	});
	$translateProvider.translations('bn', {
		ACCESS_PERMISSION : 'প্রবেশ অনুমতি',
		ACCOUNTS : 'হিসাব বহিঃ',	
		ACCOUNT_CHART : 'হিসাব তালিকা',	
		ACCOUNT_CHART : 'হিসাব তালিকা',	
		ACCOUNT_CHART_NAME : 'হিসাব তালিকার নাম',
		ACCOUNT_CHART_SETTINGS : 'হিসাব তালিকা সেটিংস',	
		ACCOUNT_EQUATION: 'হিসাব সমীকরণ',	
		ACCOUNT_INFORMATION : 'হিসাব তথ্য',	
		ACTION : 'পদক্ষেপ',	
		ADD : 'যুক্ত করুন',	
		ADDRESS : 'ঠিকানা',
		ADDRESS_LINE_1 : 'প্রথম ঠিকানা',	
		ADDRESS_LINE_2 : 'দ্বিতীয় ঠিকানা',	
		ADD_ACCOUNT_CHART : 'হিসাব তালিকা যুক্ত করুন',	
		ADD_BRANCH : 'নতুন শাখা যুক্ত করুন',	
		ADD_EMPLOYEE : 'কর্মকর্তা নিযুক্ত করুন',	
		ADD_ITEM : 'পণ্য যুক্ত করুন',	
		ADD_JOURNAL_VOUCHER : 'জাবেদা রসিদ যুক্ত করুন',	
		ADD_NEW_UNIT : 'নতুন একক যুক্ত করুন',	
		ADD_PURCHASE_ORDER : 'ক্রয় হুন্ডি যুক্ত করুন',	
		ADD_PURCHASE_ORDER: 'ক্রয় হুন্ডি যুক্ত করুন',	
		ADD_PURCHASE_VOUCHER : 'ক্রয় রশিদ যুক্ত করুন',	
		ADD_SALE_ORDER : 'বিক্রয় হুন্ডি যুক্ত করুন',	
		ADD_SALE_VOUCHER : 'বিক্রয় রশিদ যুক্ত করুন',	
		ADD_SPACE_SYMBOL_AMMOUNT : 'প্রতীক ও পরিমাণের মাঝে ফাকা স্থান  রাখুন',	
		ADD_STOCK_IN : 'মজুদ সংযুক্ত করন ',	
		ADD_STOCK_ITEM : 'মজুদ পণ্য  যুক্ত করুন',
		ADD_VOUCHER : 'রসিদ যুক্ত করুন',	
		ALIUS : 'অন্য নাম',	
		ALL_FIELDS_ARE_REQUIRED : 'সবগুলো তথ্য পূরণ করুন',	
		ALL_ORDER : 'সকল হুন্ডি',	
		ALT_MOBILE : 'অন্য একটি মোবাইল',
		ALL_VOUCHER : 'সকল ভাউচার',
		AMOUNT : 'পরিমাণ',	
		APPROVED_BY : 'স্বীকৃত কারক',	
		AVAILABLE_QUANTITY : 'ব্যবহারযোগ্য পরিমাণ',	
		BALANCE_SHEET : 'হিসাবনিকাশ বহিঃ',
		BASIC_CURRENCY_SYMBOL : 'সাধারণ মুদ্রা প্রতীক',	
		BASIC_INFORMATION : 'সাধারণ তথ্য',
		BODY : 'মূল কাঠামো',
		BRANCH : 'শাখা',	
		BRANCH_ADDRESS : 'শাখা ঠিকানা',	
		BRANCH_DESCRIPTION : 'শাখা বিবরণী',	
		BRANCH_NAME : 'শাখার নাম',	
		BRANCH_SETTINGS : 'শাখা সেটিংস',	
		BUTTON_LANG_BN : 'বাংলা',	
		BUTTON_LANG_EN : 'ইংরেজি',	
		CANCEL : 'বাতিল করুন',	
		CANCEL_ON : 'বাতিল হল',	
		CANCEL_PURCHASE_ORDER : 'ক্রয় হুন্ডি বাতিল করুন',
		CANCEL_SALE_ORDER : 'বিক্রয় হুন্ডি বাতিল করুন',	
		CATEGORY : 'শ্রেণী',	
		CHANGE_PASSWORD : 'পাসওয়ার্ড পরিবর্তন করুন',	
		CHANGE_PASSWORD: 'নতুন পাসওয়ার্ড সংযুক্ত করুন',	
		CHART_OF_ACCOUNTING : 'হিসাব তালিকা',	
		CHECK_TOTAL : 'মোট চেক করুন',	
		CITY : 'শহর',	
		CLOSE : 'বন্ধ করুন',	
		CLOSE_THE_SIDES : 'পার্শ্ব প্রদর্শনী বন্ধ করুন',	
		CODE : 'সংকেত পদ্ধতি',
		COMPANY_BRANCHES : 'প্রতিষ্ঠানের শাখা সমূহ',	
		CONSUMPTION : 'Consumption',	
		COMPANY_ID : 'প্রাতিষ্ঠানিক আই.ডি.',
		COMPANY_LOGO : 'প্রতিষ্ঠানের লোগো',	
		COMPANY_NAME : 'প্রতিষ্ঠানের নামঃ',
		COMPANY_SETTINGS : 'প্রতিষ্ঠান সেটিং',	
		COMPOUND : 'যৌগিক',	
		CONFIRM : 'নিশ্চিত করুন',	
		CONTACT_INFORMATION : 'যোগাযোগ সম্পর্কিত তথ্য',
		CONTACT_PERSON_NAME : 'যোগাযোগকারি ব্যাক্তির নাম',	
		COST_OF_GOODS_SOLD : 'বিক্রিত মালের ক্রয়কৃত দাম',
		CONTRA_VOUCHER : 'বিপরীতি রসিদ',	
		COUNTRY : 'দেশ',	
		CREATE : 'তৈরি করা',	
		CREATED_BY : 'তৈরি কারক',	
		CREATE_STOCK_CATEGORY : 'নতুন স্টক  শ্রেণী বিভাগ',	
		CREATE_STOCK_CATEGORY : 'মজুদের ধরণ তৈরি করুন',
		CREATION_DATE : 'তৈরির তারিখ ',
		CREDIT : 'পাওনা',	
		CREDIT_ACCOUNT : 'জমার পরিমাণ',	
		CREDIT_ACCOUNT : 'পাওনা\'র হিসাব',	
		CURRENT_ACCOUNT_BALANCE : 'বরতমান হিসাবের আর্থিক পরিমান',	
		DATE : 'তারিখ',	
		DATE_OF_BIRTH : 'জন্ম তারিখ',
		DEBIT : 'দেনা',	
		DEBIT_ACCOUNT : 'খরচের পরিমাণ',	
		DEBIT_ACCOUNT : 'দেনা\'র হিসাব',	
		DECIMEL_PLACES : 'দশমিকের অবস্থান',	
		DELETE : 'মুছে ফেলুন',	
		DELIVERY : 'বিতরণ',	
		DESCRIPTION : 'বিবরণ',	
		DISC : 'ছাড়',	
		DUE_AMOUNT : 'বাকির পরিমাণ',	
		EDIT : 'সম্পাদনা করুন',	
		EDIT_BRANCH : 'শাখা সম্পাদনা করুন',
		EDIT_ROLE : 'ভূমিকা সম্পাদনা করুন',
		EMAIL : 'ইমেইল',
		EMPLOYEE : 'কর্মচারী',	
		EMPLOYEE_ACCESS_PERMISSION : 'কর্মচারীর প্রবেশ অনুমতি সমূহ',
		EMPLOYEE_CATEGORY : 'কর্মচারীর ধরণ',	
		EMPLOYEE_ROLE : 'কর্মচারীর ভূমিকা',
		EMPLOYEE_SETTINGS : 'কর্মকর্তার সেটিংস',
		ERASE : 'মুছুন',	
		EXP : 'ই.এক্স.পি.',	
		EXPENSES : 'খরচ',	
		EXPENSE_VOUCHER : 'খরচের রসিদ',	
		FACEBOOK : 'ফেসবুক',	
		FATHER_NAME : 'বাবার নাম',
		FAX : 'ফ্যাক্স',
		FINANCIAL_YEAR : 'অর্থনৈতিক বছর  শুরুর তারিখ',
		FIRST_NAME : 'নামের ১ম অংশ',
		FIRST_PARTY : 'প্রথম পার্টি',
		FIRST_UNIT : 'প্রথম একক',
		FOO : 'আমি হলাম ফু',	
		FORMAL_CURRENCY_NAME : 'প্রচলিত মুদ্রার নাম',	
		FORMAL_NAME : 'প্রচলিত নাম',	
		FORMAL_NAME : 'প্রথাগত নাম',	
		FOUNDATION_DATE : 'প্রতিষ্ঠান গঠনের তারিখ',	
		GENDER : 'লিঙ্গ',
		GODOWN_AND_LOT : 'গোডাঊন ও লট',
		GRAND_TOTAL : 'সর্বমোট ',	
		GROSS_PROFIT : 'গ্রস প্রফিট',	
		HR_PAYROLE : 'এইচ.আর./পে-রোল',	
		ID : 'আই.ডি',	
		IMAGE_AND_LOGO_SETTINGS : 'ছবি এবং লগো',	
		INDEX : 'সূচক',	
		INVENTORY : 'দ্রব্যাদির তালিকা ফর্দ',
		INVOICE: 'চালান',	
		INCOME: 'ইনকাম স্টেটমেন্ট',	
		INVOICE_PAPER_TYPE : 'চালান পত্রের ধরণ',
		ITEM : 'পণ্য',	
		ITEM_NAME : 'পণ্যের নাম',
		JOINING_DATE : 'যোগদানের তারিখ',
		JOURNAL_VOUCHER : 'জাবেদা রসিদ',
		JOURNAL_VOUCHER : 'জাবেদার রসিদ',	
		LAST_NAME : 'নামের ২য় অংশ',
		LEDGER : 'খতিয়ান',	
		LEDGER : 'খতিয়ান',
		LEDGER_ENTRIES : 'খতিয়ানে হিসাব গ্রহণ',
		LEDGER_ENTRIES_REFERENCE : 'খতিয়ান রেফ.',
		LEDGER_GROUP : 'খতিয়ান সমষ্টি',	
		LEDGER_GROUP_SETTINGS : 'খতিয়ান সমষ্টি ',
		LEDGER_INFORMATION : 'খতিয়ান তথ্য',
		LESS_DISCOUNT : 'কম ছাড়',	
		LOCKED_BY : 'নিশ্চিত কারক',	
		LOGOUT : 'লগাউট',	
		MAINTAIN_BRANCH : 'উৎপাদন তারিখ বজায় রাখুন',
		MANUFACTURE : 'MANUFACTURE',
		MEDIA_AND_DOCUMENT : 'মিডিয়া এবং নথি',
		MFG : 'এম.এফ.জি.',
		MINIMUM_SELLING_PRICE : 'সর্বনিন্ম বিক্রয় মূল্য',
		MOBILE : 'মোবাইল',	
		MOTHER_NAME : 'মায়ের নাম',
		NAME : 'নাম',
		NAME_OF_ACCOUNT_CHART : 'হিসাব তালিকার নাম',	
		NARATION : 'বিবৃতি',	
		NEW : 'নতুন',
		NET_PROFIT : 'নেট প্রফিট',
		NID : 'এন.আই.ডি.',
		NO : 'নং',	
		NO_DECIMAL_PLACES : 'দশমিক পরবর্তি সংখ্যার পরিমাণ',
		NUMBER : 'নম্বর',
		OF : 'এর',
		OPENING_BALANCE : 'প্রারম্বিক অর্থ',
		ORDER : 'হুন্ডি',
		ORDER : 'হুন্ডি',
		ORDER_DATE : 'হুন্ডির তারিখ',
		ORDER_STOCK_ITEM : 'মজুদকৃত হুন্ডি পণ্য ',
		OTHER_SETTINGS : 'অন্যান্য সেটিংস',
		PAID_AMOUNT : 'পরিশোধিত পরিমাণ',
		PARENT : 'নিয়ন্ত্রক',
		PARENT_ACCOUNT : 'হিসাব নিয়ন্ত্রক',
		PARTICULERS : 'বিশদ বিবরণ',
		PARTY_ACCOUNT_NAME : 'পার্টির হিসাবের নাম',
		PASSWORD : 'পাসওয়ার্ড',
		PASSWORD_EMAIL_RESET: 'রিসেট ইমেইল পাসওয়ার্ড',	
		PASSWORD_CONFIRM : 'পুনরাই পাসওয়ার্ড',
		PAYMENT : 'পারিশ্রমিক',
		PAYMENT_VOUCHER : 'পারিশ্রমিকের রসিদ',
		PERMISSION_SET : 'অনুমুতি নির্ধারণ করুন',
		PHONE : 'ফোন',
		PREVIOUS_BALANCE : 'পূর্ববর্তী আর্থিক পরিমান',	
		PRODUCTION : 'Production',	
		PROFIT_LOSS_STATEMENT : 'লাভ ক্ষতি স্টেটমেন্ট',	
		PROPREITOR_NAME : 'প্রপাইটরের নাম',
		PURCHASE : 'ক্রয়',
		PURCHASED : 'ক্রয় কৃত',	
		PURCHASEORDER : 'বিক্রয় হুন্ডি',	
		PURCHASE_CANCELATION : 'ক্রয় বাতিল করুন',	
		PURCHASE_LEDGER : 'ক্রয় খতিয়ান',	
		PURCHASE_ORDER : 'ক্রয় হুন্ডি',	
		QTY : 'পরিমাণ',	
		QUANTITY : 'পরিমাণ',
		RATE : 'দর',
		READ : 'পর্যালোচনা করা',	
		RECEPIENT_VOUCHER : 'প্রাপক রসিদ',	
		REG : 'রেজি.নাম্বার.',	
		REORDER_QUANTITY : 'পুণঃহুন্ডির  পরিমাণ',
		REPORTS : 'বিবৃতি',
		REPORT_HEADER : 'রিপোর্ট হেডার',
		REVART : 'প্রত্যাবর্তন',
		ROLE : 'ভূমিকা',
		ROOM : 'কক্ষ',
		ROOM_NO : 'কক্ষ নং',
		ROOM_SETTINGS : 'কক্ষ সেটিংস',
		SALE : 'বিক্রয়',	
		SALE_CANCELATION : 'বিক্রয় বাতিল করন',	
		SALE_LEDGER : 'বিক্রয় খতিয়ান',	
		SALE_LEDGER : 'বিক্রয় খতিয়ান',
		SALE_ONLINE : 'অনলাইনে বিক্রয় করুন',
		SALE_ORDER : 'বিক্রয় হুন্ডি',	
		SALE_ORDER : 'বিক্রয় হুন্ডি',	
		SAVE : 'জমা করুন',	
		SAVE_ROLE : 'ভূমিকা তৈরি করুন',
		SEARCH : 'সন্ধান করুন  (নাম/আই.ডি.)',	
		SECOND_PARTY : 'দ্বিতীয় পার্টি',	
		SECOND_UNIT : 'দ্বিতীয় একক',	
		SELECT_ACCOUNT_EQUATION : 'হিসাব সমীকরণ বাছাই করুন',
		SELECT_CATEGORY : 'ধরণ নির্বাচন করুন',
		SELECT_COUNTRY : 'আপনার দেশ বাছাই করুন',	
		SELECT_CREDIT_ACCOUNT : 'খরচের হিসাব নির্বাচন করুন',
		SELECT_CUSTOM_TRANSACTION_DATE : 'লেন্দেন তারিখ পরিবর্তিত করুন',
		SELECT_DATE : 'তারিখ বাছুন',
		SELECT_DEBIT_ACCOUNT : 'জমার হিসাব নির্বাচন  করুন',
		SELECT_ROLE : 'ভূমিকা নির্বাচন করুন',
		SELECT_UNIT_TYPE : 'এককের ধরণ নির্বাচন করুন',	
		SELLING_PRICE : 'বিক্রয় মূল্য',	
		SELLING_PRICE : 'বিক্রয় মূল্য',	
		SETTINGS : 'সেটিং',
		SET_CREDIT_LIMIT : 'নূনতম খরচের সীমা',
		SHOW_AMOUNT_IN_MILLION : 'পরিমাণ মিলিয়নে প্রদর্শণ করবেন?',	
		SMS_LOG : 'এস.এম.এস. ',
		SPOUSE_NAME : 'পতি/পত্নির নাম',
		STATUS : 'স্বক্রিয়তা',
		STOCK_CATEGORY : 'মজুদের ধরণ',	
		STOCK_RECORD_BOOK : 'মজুদের Record Book',	
		STOCK_CATEGORY : 'স্টক শ্রেণী বিভাগ',
		STOCK_IN : 'মজুদ করন',	
		STOCK_ITEM : 'মজুদ পণ্য',	
		STOCK_OUT : 'মজুদ বাহির করন',
		SUBMIT : 'দাখিল করুন',
		SUB_TOTAL : 'উপ-মোট',	
		SUPPLIER_INVOICE_NUMBER : 'সরবরাহকারীর চালান নাম্বার',
		SURE_DELETE : 'আপনি কি নিশ্চিত যে মুছে ফেলবেন?',
		SURE_REVART : 'আপনি কি নিশ্চিত যে প্রত্যাবর্তন করবেন?',
		SYMBOL_SUFFIX_TO_AMOUNT : 'পরিমাণ পরবর্তী প্রতীক',
		THIS_FIELD_IS_REQUIRED : 'এটি অবশ্যই পূরণীয়',	
		TIGER_ERP_MENU : 'টাইগার ই.আর.পি. মেনু',
		TITLE : 'Title',	
		TOTAL : 'মোট',
		TOTAL_AMOUNT : 'মোট টাকা',
		TOTAL_CREDIT : 'মোট পাওনা',	
		TOTAL_DEBIT : 'মোট দেনা',
		TOTAL_SALE_AMOUNT : 'মোট বিক্রিত মালের টাকার পরিমান',
		TRANSACTIONS : 'লেনদেন',
		UNIT : 'একক',	
		UNIT_OF_MEASUREMENY : 'পরিমানের একক',
		UNIT_PRICE: 'একক দাম',	
		UNIT_SYMBLE : 'এককের প্রতীক',	
		UPDATE : 'হালনাগাদ করুন',	
		UPDATE_ACCOUNT_CHART : 'হিসাব তালিকা হালনাগাদ করুন',	
		UPDATE_ITEM : 'পণ্য হাল নাগাদ করুন',	
		UPDATE_JOURNAL_VOUCHER : 'জাবেদা রসিদ হালনাগাদ করুন',
		UPDATE_VOUCHER : 'রসিদ হালনাগাদ করুন',
		UPDATE_STOCK_CATEGORY: 'মজুদের ধরণ হাল নাগাদ  করুন',	
		UPDATE_STOCK_ITEM : 'মজুদ পণ্য হাল নাগাদ করুন',	
		UPDATE_UNIT : 'একক হাল নাগাদ করুন',	
		UPLOAD_FILE : 'ফাইল সংযুক্ত করুন',
		USER_NAME : 'ব্যবহৃত নাম',
		VAT : 'ভ্যাট  নঃ',
		VIEW_ORDER : 'হুন্ডি দেখুন',	
		VOUCHER : 'রসিদ',
		VOUCHER_TYPE : 'রসিদের ধরন',
		WEBSITE : 'ওয়েবসাইট',
		WHATS_APP : 'হোয়াটস অ্যাপ',	
		WORD_DEFINE_OF_DECIMAL_PLACES : 'দশমিক স্থানগুলির শব্দ সংজ্ঞা',
		ZIP : 'জিপ',
	});
	$translateProvider.translations('de', {
		TITLE : 'Hallo',
		FOO : 'Dies ist ein Paragraph.',
		BUTTON_LANG_EN : 'englisch',
		BUTTON_LANG_DE : 'deutsch'
	});
	$translateProvider.preferredLanguage('en');
	$translateProvider.useSanitizeValueStrategy('sanitizeParameters');
});

app.controller('testCtrl', function($scope, $log) {
});

app.controller('EmployeeAppCtrl', function($scope, $rootScope, $timeout,
		$mdSidenav, $http, $log, $apiurl, $mdDialog, $rootScope, $translate) {

	$rootScope.changeLanguage = function(key) {
		$translate.use(key);
	};

	$rootScope.languageSelect = {};
	$rootScope.language = [ {
		name : 'en'
	}, {
		name : 'bn'
	}
    ];
	$rootScope.ChangeLang = function(key) {
		$translate.use(key)
	};

	$scope.toggleLeft = buildToggler('left');

	function buildToggler(componentId) {
		return function() {
			$mdSidenav(componentId).toggle();
		};
	}

	$scope.openMenu = function($mdMenu, ev) {
		originatorEv = ev;
		$mdMenu.open(ev);
	}

	$scope.data = [ "Item 1", "Item 2", "Item 3", "Item 4" ]
	$scope.toggle = {};
	$scope.urlMapping = urlMapping;

	$rootScope.pageSize = '20';
	$rootScope.pageLimitOption = [20, 40 , 60 ];
	$rootScope.showProgressBar = false;
	$rootScope.errorMessage = 'Error';
	$rootScope.showAlert = function(ev) {
		$mdDialog.show($mdDialog.alert().clickOutsideToClose(true).title(
				'Error').textContent($rootScope.errorMessage).ariaLabel(
				'Alert Dialog Demo').ok('OK').targetEvent(ev));
	};

	// calling employee privileges
	$apiurl.privileges.get(employeePrivilegesSucessJSON);

	$rootScope.confirmDialog = 'Hello';
	

	function employeePrivilegesSucessJSON(response) {
		$rootScope.employeePrivileges = response.content;
	}
});

urlMapping = {
	helloState : {
		name : 'home',
		url : '/',
		controller : 'employeeDashboardController',
		templateUrl : 'templates/employee/dashboard.html'
	},

	homeState : {
		name : 'home2',
		url : '',
		controller : 'employeeDashboardController',
		templateUrl : 'templates/employee/dashboard.html'
	},
	employeeRoleState : {
		name : 'employeerole',
		url : '/employeerole',
		controller : 'employeeRoleManageController',
		templateUrl : 'templates/employee/employee_role.html'
	},
	accessPermissionState : {
		name : 'accessPermission',
		url : '/accessPermission',
		controller : 'accessPermissionManageController',
		templateUrl : 'templates/employee/access_permission.html'
	},
	accountChartState : {
		name : 'accountChart',
		url : '/accountChart',
		controller : 'accountChartController',
		templateUrl : 'templates/org/account_chart.html'
	},
	employeeManageState : {
		name : 'employeeManage',
		url : '/employeeManage',
		controller : 'employeeManageController',
		templateUrl : 'templates/employee/employee_manage.html'
	},
	companySettingState : {
		name : 'companySetting',
		url : '/companySetting',
		controller : 'companySettingController',
		templateUrl : 'templates/org/company_setting.html'
	},
	ledgerGroupSettingState : {
		name : 'ledgerGroup',
		url : '/ledgerGroup',
		controller : 'ledgerGroupController',
		templateUrl : 'templates/org/ledger_group_setting.html'
	},
	branchSettingState : {
		name : 'branchSetting',
		url : '/branchSetting',
		controller : 'branchSettingController',
		templateUrl : 'templates/org/branch_setting.html'
	},
	roomSettingState : {
		name : 'roomSetting',
		url : '/roomSetting',
		controller : 'roomSettingController',
		templateUrl : 'templates/org/room_setting.html'
	},
	imageAndLogoSettingState : {
		name : 'imageAndLogoSetting',
		url : '/imageAndLogoSetting',
		controller : 'companyImageAndLogoSettingController',
		templateUrl : 'templates/org/image_and_logo_setting.html'
	},
	employeeCategoryState : {
		name : 'employeeCategory',
		url : '/employeeCategory',
		controller : 'employeeCategoryController',
		templateUrl : 'templates/employee/employee_category.html'
	},
	stockCategoryState : {
		name : 'stockCategory',
		url : '/stockCategory',
		controller : 'stockCategoryManageController',
		templateUrl : 'templates/org/stock_category.html'
	},
	unitsState : {
		name : 'units',
		url : '/units',
		controller : 'unitsManageController',
		templateUrl : 'templates/org/stock/units.html'
	},
	journalVoucherSettingState : {
		name : 'journalVoucher',
		url : '/journalVoucher',
		controller : 'journalVoucherController',
		templateUrl : 'templates/org/voucher/journal_voucher_setting.html'
	},
	ledgerState : {
		name : 'ledger',
		url : '/ledger',
		controller : 'ledgerController',
		templateUrl : 'templates/org/ledger.html'
	},
	ledgerBalanceSheet : {
		name : 'ledgerBalanceSheet',
		url : '/ledger_balance_sheet',
		controller : 'ledgerBalanceSheetController',
		templateUrl : 'templates/org/ledger_balance_sheet.html'
	},
	stockItem : {
		name : 'stockItem',
		url : '/stock_item',
		controller : 'stockItemController',
		templateUrl : 'templates/org/stock/stock_item.html'
	},
	purchaseOrderState : {
		name : 'purchaseOrder',
		url : '/purchase_order',
		controller : 'purchaseOrderController',
		templateUrl : 'templates/org/order/purchase_order.html'
	},
	saleOrderState : {
		name : 'saleOrder',
		url : '/sale_order',
		controller : 'saleOrderController',
		templateUrl : 'templates/org/order/sale_order.html'
	},
	cancelPurchaseOrderState : {
		name : 'cancelPurchaseOrder',
		url : '/cancel_purchase_order',
		controller : 'cancelPurchaseOrderController',
		templateUrl : 'templates/org/order/cancel_purchase_order.html'
	},
	cancelSaleOrderState : {
		name : 'cancelSaleOrder',
		url : '/cancel_sale_order',
		controller : 'cancelSaleOrderController',
		templateUrl : 'templates/org/order/cancel_sale_order.html'
	},
	stockIn : {
		name : 'stockIn',
		url : '/stock_in',
		controller : 'stockInController',
		templateUrl : 'templates/org/order/stock_in.html'
	},
	stockOut : {
		name : 'stockOut',
		url : '/stock_out',
		controller : 'stockOutController',
		templateUrl : 'templates/org/order/stock_out.html'
	},
	passwordEmailResetRequestState : {
		name : 'passwordResetRequest',
		url : '/passwordresetrequest',
		controller : 'passwordEmailResetrequestcontroller',
		templateUrl : 'templates/employee/email_password_reset.html'
	},
	changePasswordState : {
		name : 'changePassword',
		url : '/change_password',
		controller : 'changePasswordController',
		templateUrl : 'templates/employee/change_password.html'
	},
	smsState : {
		name : 'smsState',
		url : '/sms',
		controller : 'smsController',
		templateUrl : 'templates/org/reports/sms.html'
	},
	productionState : {
		name : 'productionState',
		url : '/production',
		controller : 'productionStateController',
		templateUrl : 'templates/org/order/production.html'
	},
	consumptionState : {
		name : 'consumptionState',
		url : '/consumption',
		controller : 'consumptionStateController',
		templateUrl : 'templates/org/order/consumption.html'
	},
	profitAndLossStatementState : {
		name : 'profit_loss_statement',
		url : '/profit_loss_statement',
		controller : 'profitAndLossStatementController',
		templateUrl : 'templates/org/reports/profit_loss_statement_realtime.html'
	},
	incomeStatementState : {
		name : 'income_statement',
		url : '/income_statement',
		controller : 'incomeStatementController',
		templateUrl : 'templates/org/reports/profit_loss_statement.html'
	},
	stockRecordBookState : {
		name : 'stockRecordBookState',
		url : '/stock_record_book',
		controller : 'stockRecordBookController',
		templateUrl : 'templates/org/reports/stock_record_book.html'
	}
}

app.config(function($stateProvider) {

	$stateProvider.state(urlMapping.helloState);
	$stateProvider.state(urlMapping.homeState);
	$stateProvider.state(urlMapping.employeeRoleState);
	$stateProvider.state(urlMapping.accessPermissionState);
	$stateProvider.state(urlMapping.employeeManageState);
	$stateProvider.state(urlMapping.companySettingState);
	$stateProvider.state(urlMapping.ledgerGroupSettingState);
	$stateProvider.state(urlMapping.branchSettingState);
	$stateProvider.state(urlMapping.roomSettingState);
	$stateProvider.state(urlMapping.imageAndLogoSettingState);
	$stateProvider.state(urlMapping.employeeCategoryState);
	$stateProvider.state(urlMapping.stockCategoryState);
	$stateProvider.state(urlMapping.unitsState);
	$stateProvider.state(urlMapping.accountChartState);
	$stateProvider.state(urlMapping.journalVoucherSettingState);
	$stateProvider.state(urlMapping.ledgerState);
	$stateProvider.state(urlMapping.ledgerBalanceSheet);
	$stateProvider.state(urlMapping.stockItem);
	$stateProvider.state(urlMapping.purchaseOrderState);
	$stateProvider.state(urlMapping.saleOrderState);
	$stateProvider.state(urlMapping.cancelPurchaseOrderState);
	$stateProvider.state(urlMapping.cancelSaleOrderState);
	$stateProvider.state(urlMapping.stockIn);
	$stateProvider.state(urlMapping.stockOut);
	$stateProvider.state(urlMapping.changePasswordState);
	$stateProvider.state(urlMapping.passwordEmailResetRequestState);
	$stateProvider.state(urlMapping.smsState);
	$stateProvider.state(urlMapping.productionState);
	$stateProvider.state(urlMapping.consumptionState);
	$stateProvider.state(urlMapping.profitAndLossStatementState);
	$stateProvider.state(urlMapping.incomeStatementState);
	$stateProvider.state(urlMapping.stockRecordBookState);
});
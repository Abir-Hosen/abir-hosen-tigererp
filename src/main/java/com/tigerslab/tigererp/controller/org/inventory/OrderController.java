package com.tigerslab.tigererp.controller.org.inventory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.financial.LedgerAccounts;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.inventory.Order;
import com.tigerslab.tigererp.model.inventory.OrderStockItem;
import com.tigerslab.tigererp.model.inventory.StockItem;
import com.tigerslab.tigererp.model.org.Company;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.model.user.employee.Employee;
import com.tigerslab.tigererp.repository.financial.LedgerAccountsRepository;
import com.tigerslab.tigererp.service.financial.LedgerAccountsService;
import com.tigerslab.tigererp.service.org.CompanyProfileService;
import com.tigerslab.tigererp.service.org.inventory.OrderService;
import com.tigerslab.tigererp.service.user.UserService;
import com.tigerslab.tigererp.sms.TigerERPSMSSender;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	private Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	OrderService service;
	@Autowired
	UserService userService;
	@Autowired
	CompanyProfileService companyProfileService;
	@Autowired
	InvoiceConstant invoiceConstant;
	@Autowired
	AccessPermission accessPermission;

	@Autowired
	LedgerAccountsRepository ledgerAccountsRepository;
	
	@Autowired
	private TigerERPSMSSender tigerERPSMSSender;
	
	@Autowired
	private LedgerAccountsService ledgerAccountsService;

	@GetMapping
	public ResponseEntity<Page<Order>> findAllBySortAndOrder(RequestParameter requestParameter, HttpSession session) {
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(accessPermission.getReadAllOrderSettings()) {
			logger.info("Entering GET method");
			Page<Order> page = service.findAllBySortAndOrder(requestParameter);
			return ResponseEntity.ok(page);
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/productTransactionType/{id}")
	public ResponseEntity<Page<Order>> findByOrderStockItemProductTransactionEntryTypeSortAndOrder(@PathVariable Long id, @RequestParam(required= false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate, RequestParameter requestParameter, HttpSession session) {

		logger.info("Entering ORDER GET method");
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if((!accessPermission.getReadPurchaseOrderSettings() && id== (long)1) 
				|| (!accessPermission.getReadSaleOrderSettings() && id== (long)2)
				|| (!accessPermission.getReadStockInSettings() && id== (long)3) 
				|| (!accessPermission.getReadStockOutSettings() && id== (long)4)
				|| (!accessPermission.getReadCancelPurchaseOrderSettings() && id== (long)5) 
				|| (!accessPermission.getReadCancelSaleOrderSettings() && id== (long)6
				|| (!accessPermission.getReadProductionSettings() && id== (long)7) 
				|| (!accessPermission.getReadConsumptionSettings() && id== (long)8))) {
			
			return ResponseEntity.badRequest().build();
		}else {
			Date currentDate = new Date();
			Calendar calender = Calendar.getInstance();
			
			if(startDate!=null) {
				startDate.setDate(startDate.getDate()+1);
			}else {
	        	calender.setTime(currentDate);
	        	calender.set(1700, 10, 10);
	        	startDate = calender.getTime();
			}
			if(endDate !=null) {
				endDate.setDate(endDate.getDate()+1);
			}else {
	        	calender.setTime(currentDate);
	        	calender.add(calender.DATE, 2);
	        	endDate = calender.getTime();
			}
			Page<Order> page = service.findByOrderStockItemProductTransactionEntryTypeSortAndOrder(id, startDate, endDate, requestParameter);
			return ResponseEntity.ok(page);
		}
	}
	
	@GetMapping("/quantity/{id}")
	public ResponseEntity<Page<OrderStockItem>> findByStockItemByQuantity(@PathVariable Long id, @RequestParam(required= false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate, RequestParameter requestParameter, HttpSession session) {
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadStockItemSettings()) {
			return ResponseEntity.badRequest().build();
		}else {
			startDate.setDate(startDate.getDate()+1);
			endDate.setDate(endDate.getDate()+1);
        	
			logger.info("Entering GET method");
			
			Page<OrderStockItem> page = null;
			if(id==0) {
				page = service.findAllStockItemByQuantity(startDate, endDate, requestParameter);
			}else{
				page = service.findStockItemByQuantity(id, startDate, endDate, requestParameter);
			}
			return ResponseEntity.ok(page);
		}
	}
	
	@GetMapping("/pdfVoucher/{id}")
	public ResponseEntity findByPdfOrder(@PathVariable Long id,@RequestParam(required = false) Boolean ship, HttpSession session) throws IOException {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadInvoiceSettings()) {
			return ResponseEntity.badRequest().build();
		}
		Boolean chalan = false;
		if(ship!=null && ship) {
			chalan=true;
			System.out.println("\n\n\n\n ####### here chalan is true ############# \n\n\n");
		}
		logger.info("Entering GET PDF method");
		
		Optional<Company> checkCompany = companyProfileService.findById((long)1);
		Company company = new Company();
		Optional<Order> checkOrder = service.findById(id);
		Order order = new Order();
		
		if(!checkCompany.isPresent()) {
			return invoiceConstant.errorSetup("ERROR # There is no setup for company!", id);
		}else {
			company = checkCompany.get();
			if(company.getPaperType() == null) {
				return invoiceConstant.errorSetup("ERROR # Here no paper type for invoice is created!", id);
			}
		}
		if(!checkOrder.isPresent()) {
			return invoiceConstant.errorSetup("ERROR # There is no order for this id # "+id, id);
		}else {
			order = checkOrder.get();
		}
		
		
		Float width = company.getPaperType().getWidth();
		Float height = company.getPaperType().getHeight();
		Float dpi = 72f;
		Float unit = (float) 1/8;

		Float orderFooterHeight;
		Float orderHeaderHeight;
		Float orderSummeryHeight;
		Float dataTableHeight;
		Float dataPerRowHeight;
		Float dataRowHeightRequire;
		Float lastPageLastSpace;

		Integer dataRowPossiblePerPage;
		Integer orderItemQuantity = order.getStockItems().size();//51
		Integer stockItemIndex;
		Integer pageRequire;
		Integer lastPageLastRow;
		
		PDRectangle size = new PDRectangle(width*dpi, height*dpi);
		
		PDDocument doc = new PDDocument();
		List<PDPage> pages = new ArrayList<>();
		List<PDPageContentStream> contentStreams = new ArrayList<>();
		
		if(width>=5.8 && width<=8.5) {
			
			orderHeaderHeight = 4f;
			dataPerRowHeight = (float) 3/8;
			orderSummeryHeight = 1f+ (float) 3/8;
			orderFooterHeight = 0.5f;
			dataTableHeight = height-orderHeaderHeight-orderFooterHeight;//inch value
			dataRowHeightRequire = orderItemQuantity*dataPerRowHeight;//inch value
			
			dataRowPossiblePerPage = (int) Math.floor(dataTableHeight.doubleValue() / dataPerRowHeight.doubleValue());
			pageRequire = (int) (Math.ceil(orderItemQuantity.doubleValue() / dataRowPossiblePerPage.doubleValue()));
			lastPageLastRow =  (orderItemQuantity % dataRowPossiblePerPage);
			lastPageLastSpace =  (dataRowPossiblePerPage-(orderItemQuantity % dataRowPossiblePerPage))*dataPerRowHeight;
			
			if(lastPageLastSpace <= orderSummeryHeight || orderItemQuantity % dataRowPossiblePerPage == 0) {
				pageRequire = pageRequire+1;
				lastPageLastRow=0;
			}
			
			System.out.println("pageRequire: "+pageRequire);
			System.out.println("dataRowPossiblePerPage: "+dataRowPossiblePerPage);
			System.out.println("lastPageLastSpace: "+lastPageLastSpace);
			System.out.println("lastPageLastRow: "+lastPageLastRow);
			
			for(int i=0; i<pageRequire ; i++) {
			    pages.add(new PDPage(size));
			    contentStreams.add(new PDPageContentStream(doc, pages.get(i)));
			}

			for(int i=0; i<pageRequire; i++) {
				
				invoiceConstant.constantView(doc, unit, dpi, i, dataPerRowHeight, width, height, orderHeaderHeight, orderFooterHeight, id,  contentStreams.get(i), company, order, chalan);

				Float yTop = (height-orderHeaderHeight)*dpi;
				if(i<pageRequire-1) {
					if(orderItemQuantity % dataRowPossiblePerPage != 0) {//--------------------problem
						if(i<pageRequire-1 && lastPageLastSpace > orderSummeryHeight) {
							for(int j=0; j<dataRowPossiblePerPage; j++) {
								
								stockItemIndex = ((i*dataRowPossiblePerPage)+j);
								invoiceConstant.dataList(doc, unit, dpi, i, dataPerRowHeight, width, yTop, contentStreams.get(i), stockItemIndex, order, chalan);
								
								yTop = yTop-(dataPerRowHeight*dpi);
							}
						}else {
							for(int j=0; j<orderItemQuantity % dataRowPossiblePerPage; j++) {
								
								stockItemIndex = ((i*dataRowPossiblePerPage)+j);
								invoiceConstant.dataList(doc, unit, dpi, i, dataPerRowHeight, width, yTop, contentStreams.get(i), stockItemIndex, order, chalan);
								
								yTop = yTop-(dataPerRowHeight*dpi);
							}
						}
					}else {
						for(int j=0; j<dataRowPossiblePerPage; j++) {
							
							stockItemIndex = ((i*dataRowPossiblePerPage)+j);
							invoiceConstant.dataList(doc, unit, dpi, i, dataPerRowHeight, width, yTop, contentStreams.get(i), stockItemIndex, order, chalan);	
							
							yTop = yTop-(dataPerRowHeight*dpi);
						}
						
					}
				}else {
					yTop = (height-orderHeaderHeight)*dpi;
					if(lastPageLastRow!=0) {
						for(int j=0; j<lastPageLastRow; j++) {
							
							stockItemIndex = ((i*dataRowPossiblePerPage)+j);
							invoiceConstant.dataList(doc, unit, dpi, i, dataPerRowHeight, width, yTop, contentStreams.get(i), stockItemIndex, order, chalan);
							
							if(j==lastPageLastRow-1) {
								invoiceConstant.dataSummery(doc, unit, dpi, i, dataPerRowHeight, width, yTop, contentStreams.get(i), order, chalan);
							}
							yTop = yTop-(dataPerRowHeight*dpi);
						}
					}else {
						yTop = yTop+(dataPerRowHeight*dpi);
						invoiceConstant.dataSummery(doc, unit, dpi, i, dataPerRowHeight, width, yTop, contentStreams.get(i), order, chalan);
					}
				}
				contentStreams.get(i).close();
			}
			
		}else if(width<5.8) {
			orderHeaderHeight = 6f;
			orderFooterHeight = 0.5f;
			dataTableHeight = width-orderHeaderHeight-orderFooterHeight;
			
			return invoiceConstant.errorSetup("ERROR # Currently there is no setup for this paper width # "+ width + " inch", id);
		}

		for(int i=0; i<pages.size(); i++) {
			doc.addPage(pages.get(i));
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		doc.save(baos);
		doc.close();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "pdf"));
		headers.setContentLength(baos.toByteArray().length);
		
		return new ResponseEntity(baos.toByteArray(), headers, HttpStatus.CREATED);
	}
	
	@PostMapping
	public ResponseEntity<Order> create(@RequestBody @Valid Order order, BindingResult bindingResult, HttpSession session) {

		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		Long id = order.getStockItems().iterator().next().getProductTransactionEntryType().getId();
		System.out.println("ORD: "+order);
		
		if((!accessPermission.getCreatePurchaseOrderSettings() && id== (long)1) || (!accessPermission.getCreateSaleOrderSettings() && id== (long)2)
				|| (!accessPermission.getCreateStockInSettings() && id== (long)3) || (!accessPermission.getCreateStockOutSettings() && id== (long)4)
				|| (!accessPermission.getCreateCancelPurchaseOrderSettings() && id== (long)5) 
				|| (!accessPermission.getCreateCancelSaleOrderSettings() && id== (long)6)
				|| (!accessPermission.getCreateProductionSettings() && id== (long)7) 
				|| (!accessPermission.getCreateConsumptionSettings() && id== (long)8)) {
			return ResponseEntity.badRequest().build();
		}
		
//		Employee employee = userService.findUserByEmail((String)session.getAttribute("name")).getEmployee().iterator().next();
//		if(employee!=null) {
//			order.setSalesHandlyBy(employee);
//			System.out.println("By the way!");
//		}
		logger.info("Entering POST Method");
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		else {
			BigDecimal previousBalance = ledgerAccountsRepository.findByLedgerAccountsWithBalance(order.getParty().getId());
			Optional<LedgerAccounts> orderParty = ledgerAccountsService.findById((long)order.getParty().getId());
			
			Order orderSuccess = service.save(order);
			if(order.getSendSMS() == null) {
				return ResponseEntity.ok(orderSuccess);
			}
			if(order.getSendSMS() == false) {
				return ResponseEntity.ok(orderSuccess);
			}
			System.out.println("------------------------------ Getting Country Code -------------");
			String mobileNum = "";
			String countryCode = "";
			String fullNumber = "";
			if(orderParty.isPresent()) {
				if(orderParty.get().getAccountAddress().iterator().hasNext()) {
					mobileNum = orderParty.get().getAccountAddress().iterator().next().getMobilePhone().getNumber();
					countryCode = "+"+orderParty.get().getAccountAddress().iterator().next().getMobilePhone().getCountry().getPhoneCode();
					fullNumber = countryCode.concat(mobileNum);
				}

			}
			else {
				System.out.println("Not exist account");
			}

			System.out.println(fullNumber);
			
			if(orderSuccess.getStockItems().iterator().next().getProductTransactionEntryType().getName().equals(ConstantFactory.SALE)) {
				System.out.println("THIS IS SALE ORDER");
				//BigDecimal previousBalance = ledgerAccountsRepository.findByLedgerAccountsWithBalance(party.getId());
				
				Optional<Company> compnay = companyProfileService.findById((long)1);
				String companyName = "";
				int decimalPlaces = 2;
				if(compnay.isPresent()) {
					decimalPlaces = compnay.get().getNumberOfDecimalPlaces();
					companyName = compnay.get().getCompanyName().trim();
					if(companyName.length() > 36) {
						companyName.substring(0, 35);
					}
					
				}
				LedgerAccounts party = order.getParty();
				//BigDecimal previousBalance = ledgerAccountsRepository.findByLedgerAccountsWithBalance(party.getId());
				
				BigDecimal total = new BigDecimal(0);
				BigDecimal paidAmount = new BigDecimal(0);
				paidAmount = orderSuccess.getPaidAmount();
				paidAmount = paidAmount.setScale(decimalPlaces, BigDecimal.ROUND_HALF_EVEN);
				for (OrderStockItem  item: orderSuccess.getStockItems()) {
					BigDecimal productPrice = item.getRate();
					BigDecimal discountRate = item.getDiscount();
					BigDecimal quantity = new BigDecimal(item.getQuantity());
					if(item.getQuantity() < 0) {
						quantity = quantity.multiply( new BigDecimal(-1));
					}
					
					BigDecimal totalProductPrice = productPrice.multiply(quantity);
					BigDecimal totalDiscount = totalProductPrice.multiply(discountRate).divide(new BigDecimal(100));
					BigDecimal totalPriceWithDisBigDecimal = totalProductPrice.subtract(totalDiscount);
					total = total.add(totalPriceWithDisBigDecimal);
				}
				total = total.setScale(decimalPlaces, BigDecimal.ROUND_HALF_EVEN);
				BigDecimal presentBalance = previousBalance.add(total).subtract(paidAmount);
				presentBalance = presentBalance.setScale(decimalPlaces, BigDecimal.ROUND_HALF_EVEN);
				String smsTemplate = "Dear Sir, Your Bill Amount "+total+", Paid Amount "+paidAmount+" Prev. Balance "+previousBalance+", Present Balance "+presentBalance+",Thank You!!\n("+companyName+")";
				System.out.println("\n\n"+smsTemplate+"\n\n");
				
				try {
					tigerERPSMSSender.SendSMS(fullNumber, smsTemplate);
				}catch(Exception ex){
					
				}
				tigerERPSMSSender.saveSMSInfo(fullNumber, smsTemplate, order.getParty());
			}
			else if(orderSuccess.getStockItems().iterator().next().getProductTransactionEntryType().getName().equals(ConstantFactory.PURCHASE)) {
				System.out.println("THIS IS PURCHASE ORDER");
				Optional<Company> compnay = companyProfileService.findById((long)1);
				String companyName = "";
				int decimalPlaces = 2;
				if(compnay.isPresent()) {
					decimalPlaces = compnay.get().getNumberOfDecimalPlaces();
					companyName = compnay.get().getCompanyName().trim();
					if(companyName.length() > 36) {
						companyName.substring(0, 35);
					}
					
				}
				LedgerAccounts party = order.getParty();
				//BigDecimal previousBalance = ledgerAccountsRepository.findByLedgerAccountsWithBalance(party.getId());
				
				BigDecimal total = new BigDecimal(0);
				BigDecimal paidAmount = new BigDecimal(0);
				paidAmount = orderSuccess.getPaidAmount();
				paidAmount = paidAmount.setScale(decimalPlaces, BigDecimal.ROUND_HALF_EVEN);
				for (OrderStockItem  item: orderSuccess.getStockItems()) {
					BigDecimal productPrice = item.getRate();
					BigDecimal discountRate = item.getDiscount();
					BigDecimal quantity = new BigDecimal(item.getQuantity());
					
					BigDecimal totalProductPrice = productPrice.multiply(quantity);
					BigDecimal totalDiscount = totalProductPrice.multiply(discountRate).divide(new BigDecimal(100));
					BigDecimal totalPriceWithDisBigDecimal = totalProductPrice.subtract(totalDiscount);
					total = total.add(totalPriceWithDisBigDecimal);
				}
				total = total.setScale(decimalPlaces, BigDecimal.ROUND_HALF_EVEN);
				BigDecimal presentBalance = previousBalance.add(total).subtract(paidAmount);
				presentBalance = presentBalance.setScale(decimalPlaces, BigDecimal.ROUND_HALF_EVEN);
				String smsTemplate = "Dear Sir, Last Bill Amount "+total+", Paid Amount "+paidAmount+" Prev. Balance "+previousBalance+", Present Balance "+presentBalance+",Thank You!!\n("+companyName+")";
				System.out.println("\n\n"+smsTemplate+"\n\n");
				
				try {
					tigerERPSMSSender.SendSMS(fullNumber, smsTemplate);
				}catch(Exception ex){
					
				}
				tigerERPSMSSender.saveSMSInfo(fullNumber, smsTemplate, order.getParty());
			}
			
			return ResponseEntity.ok(orderSuccess);
		}
	}
	
	private void findById(long l) {
		// TODO Auto-generated method stub
		
	}

	@PutMapping("/{id}")
	public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody @Valid Order order, BindingResult bindingResult, HttpSession session) {

		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		Long oId = order.getStockItems().iterator().next().getProductTransactionEntryType().getId();

		if((!accessPermission.getUpdatePurchaseOrderSettings() && oId== (long)1) || (!accessPermission.getUpdateSaleOrderSettings() && oId== (long)2)
				|| (!accessPermission.getUpdateStockInSettings() && oId== (long)3) || (!accessPermission.getUpdateStockOutSettings() && oId== (long)4)
				|| (!accessPermission.getUpdateCancelPurchaseOrderSettings() && oId== (long)5) 
				|| (!accessPermission.getUpdateCancelSaleOrderSettings() && oId== (long)6)
				|| (!accessPermission.getUpdateProductionSettings() && oId== (long)7) 
				|| (!accessPermission.getUpdateConsumptionSettings() && oId== (long)8) ) {
			return ResponseEntity.badRequest().build();
		}
		
		logger.info("Entering Put Method");
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		else {
			service.save(order);
			return ResponseEntity.ok(order);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Order> delete(@PathVariable Long id, HttpSession session) {
		
		Order order = service.findById(id).get();
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		Long oId = order.getStockItems().iterator().next().getProductTransactionEntryType().getId();

		if((!accessPermission.getDeletePurchaseOrderSettings() && oId== (long)1) || (!accessPermission.getDeleteSaleOrderSettings() && oId== (long)2)
				|| (!accessPermission.getDeleteStockInSettings() && oId== (long)3) || (!accessPermission.getDeleteStockOutSettings() && oId== (long)4)
				|| (!accessPermission.getDeleteCancelPurchaseOrderSettings() && oId== (long)5) 
				|| (!accessPermission.getDeleteCancelSaleOrderSettings() && oId== (long)6)
				|| (!accessPermission.getDeleteProductionSettings() && oId== (long)7) 
				|| (!accessPermission.getDeleteConsumptionSettings() && oId== (long)8)) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering Delete Method");
		service.deleteById(id);
		return ResponseEntity.ok(null);
	}

}

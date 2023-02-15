package com.tigerslab.tigererp.controller.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tigerslab.tigererp.controller.org.inventory.OrderController;
import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.financial.LedgerAccounts;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.inventory.Order;
import com.tigerslab.tigererp.model.inventory.OrderStockItem;
import com.tigerslab.tigererp.model.inventory.StockItem;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.service.financial.LedgerAccountsService;
import com.tigerslab.tigererp.service.org.inventory.OrderService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

	private Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	AccessPermission accessPermission;
	@Autowired
	private LedgerAccountsService ledgerAccountsService;
	@Autowired
	OrderService orderService;

	@RequestMapping(value="/account/{id}", method = RequestMethod.GET)
	public ResponseEntity<Page<LedgerAccounts>> findByAccountId(@PathVariable Long id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate, RequestParameter requestParameter, HttpSession session) {

		startDate.setDate(startDate.getDate()+1);
		endDate.setDate(endDate.getDate()+1);
		
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
		System.out.println(accountTransaction.getContent());
		return ResponseEntity.ok(accountTransaction);
	}

	
	@GetMapping("/paidAmount/{id}")
	public ResponseEntity<Page<Order>> findPaidAmountByPartyId(@PathVariable Long id, @RequestParam(required= false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate, RequestParameter requestParameter, HttpSession session) {
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		
			startDate.setDate(startDate.getDate()+1);
			endDate.setDate(endDate.getDate()+1);
        	
			logger.info("Entering GET method");
			
			Page<Order> page = orderService.findPaidAmountByPartyId(id, startDate, endDate, requestParameter);
			
			return ResponseEntity.ok(page);
		
	}

	@GetMapping("/parent/{id}")
	public ResponseEntity<Page> findAllLedgerAccountsByParent(@PathVariable Long id, HttpSession session) {
		
		logger.info("Entering GET method");
		
		ArrayList<Long> arrlist = new ArrayList<Long>();
		Long number = ledgerAccountsService.findLedgerAccountsByParentId(id);
		arrlist.add(number);
		Page number2 = new PageImpl(arrlist);
		return ResponseEntity.ok(number2);
	}

	@GetMapping("/totalSell")
	public ResponseEntity<Page<StockItem>> findByStockItemByQuantity(@RequestParam(required= false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate, RequestParameter requestParameter, HttpSession session) {
		
			startDate.setDate(startDate.getDate()+1);
			endDate.setDate(endDate.getDate()+1);
        	
			logger.info("Entering GET method");
			
			Page<StockItem> page = orderService.findAllStockItemByTotalSell(startDate, endDate, requestParameter);
			return ResponseEntity.ok(page);
		
	}

	@GetMapping("/quantityByDate/{id}")
	public ResponseEntity<Page<OrderStockItem>> findQuantityByTypeByDate(@PathVariable Long id, @RequestParam(required= false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required= false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
			RequestParameter requestParameter, HttpSession session) {
			
			Page<OrderStockItem> page = orderService.findQuantityByTypeByDate(id, startDate, endDate, requestParameter);
			return ResponseEntity.ok(page);
	}

}

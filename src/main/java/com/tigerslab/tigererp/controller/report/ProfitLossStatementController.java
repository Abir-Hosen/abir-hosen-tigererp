package com.tigerslab.tigererp.controller.report;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.custom.OrderTotalByDateRange;
import com.tigerslab.tigererp.model.financial.LedgerAccounts;
import com.tigerslab.tigererp.model.financial.LedgerEntries;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.inventory.Order;
import com.tigerslab.tigererp.model.inventory.OrderStockItem;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.service.financial.LedgerAccountsService;
import com.tigerslab.tigererp.service.financial.LedgerEntriesService;
import com.tigerslab.tigererp.service.org.inventory.OrderService;

@RestController
@RequestMapping("/api/profitloss")
public class ProfitLossStatementController {
	
	@Autowired
	private LedgerEntriesService ledgerEntriesService;
	@Autowired
	AccessPermission accessPermission;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private LedgerAccountsService ledgerAccountsService;
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Page<?>> findByProfitAndLossRealTime(@PathVariable Long id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate, RequestParameter requestParameter, HttpSession session) {
		System.out.println("Call PROFIT LOSS CTRL");
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadProfitLossStatementSettings()) {
			return ResponseEntity.badRequest().build();
		}
			
		Page<?> totalSaleOrder = orderService.findByOrderStockItemForProfitLoss((long)2, startDate, endDate, requestParameter);
		Page<?> totalPurchaseOrder = null;
				
		OrderTotalByDateRange item = null;
		double totalSaleAmt = 0.00; // going to front end
		BigDecimal purchaseAmountTotal = new BigDecimal(0); // go to front end
		BigDecimal totalPurchaseAmt = new BigDecimal(0); 
		
		for(int i=0; i<totalSaleOrder.getContent().size(); i++) { // sum total sale amount
			 item = (OrderTotalByDateRange)totalSaleOrder.getContent().get(i);
			 totalSaleAmt = totalSaleAmt+item.getTotalAmount();
			 
			 //inner loop purpose code
			 double saleQuantity = 0.00;
			 double purchaseQuantity = 0.00;
			 BigDecimal purchaseAmountSingle = new BigDecimal(0);
			 saleQuantity = item.getQuantity();
			 long itemId = item.getOrder().getStockItems().iterator().next().getStockItem().getId();
			 Page<Order> totalPurchase = orderService.findByOrderStockItemSpecificProduct((long)1,itemId, requestParameter);

			 for(int j=0; j <totalPurchase.getContent().size(); j++ ) { // find out purchase price of goods
				 Order purchaseOrder = (Order)totalPurchase.getContent().get(j);
				 for(int k=0; k < purchaseOrder.getStockItems().size(); k++) {
					 if(purchaseOrder.getStockItems().get(k).getStockItem().getId() == itemId) {
						 OrderStockItem orderStockItem = purchaseOrder.getStockItems().get(k);
						 
						 double currentQuantity = orderStockItem.getQuantity();
						 BigDecimal itemAmount = orderStockItem.getRate().multiply(new BigDecimal(orderStockItem.getQuantity()));
						 BigDecimal discount = itemAmount.multiply(orderStockItem.getDiscount()).divide(new BigDecimal(100));
						 BigDecimal totalAmount = itemAmount.subtract(discount);
						 purchaseQuantity = purchaseQuantity + currentQuantity;	
						 
						 
						 if(purchaseQuantity == saleQuantity) {
							 totalPurchaseAmt = totalAmount;
							 purchaseAmountTotal = purchaseAmountTotal.add(totalAmount);
							 break;
						 }
						 if(purchaseQuantity > saleQuantity) {
							 double difference = purchaseQuantity-saleQuantity;
							
							 BigDecimal PriceEqualtoSaleQuantity = totalAmount.divide(new BigDecimal(currentQuantity)).multiply(new BigDecimal(difference));
							 purchaseAmountSingle = purchaseAmountSingle.add(totalAmount);
							 purchaseAmountSingle = purchaseAmountSingle.subtract(PriceEqualtoSaleQuantity);
						
							 totalPurchaseAmt = purchaseAmountSingle;
							 System.out.println("i: "+i+"j: "+j+"Product: "+orderStockItem.getStockItem().getName()+"Total: "+totalPurchaseAmt);
							 purchaseAmountTotal = purchaseAmountTotal.add(totalPurchaseAmt);
							 break;
						 }
						 
						 purchaseAmountSingle = purchaseAmountSingle.add(totalAmount);
						 	
						
					 } // end if
				 } // end k (inside one order, iterate single order)
				 if(purchaseQuantity == saleQuantity) {
					 break;
				 }
				 if(purchaseQuantity > saleQuantity) {
					 break;
				 }
			 } //end j, itterate each order
		} // end i
		
		System.out.println("Total Sale Amt: "+totalSaleAmt);
		System.out.println("total Purchase Amt: "+purchaseAmountTotal);
		
		// find expenses
		//Page<LedgerEntries> expenses = ledgerEntriesService.findBySuperParent((long)5, requestParameter);
		//System.out.println("Expenses: "+expenses);
		
		Page<LedgerEntries> ledgerEntries = ledgerEntriesService.findBySuperParentByDate((long) 5, startDate, endDate, requestParameter);
		System.out.println("Expenses Acc: "+ledgerEntries.getContent());
		BigDecimal eAmount = new BigDecimal(0);
		//some all expense account balance
		for(int i=0; i<ledgerEntries.getContent().size(); i++) {
			eAmount.add(ledgerEntries.getContent().get(i).getTransactions().getAmount());
			System.out.println(ledgerEntries.getContent().get(i).getTransactions().getAmount());
		}
		System.out.println(eAmount);
		//Page<LedgerAccounts> accountTransaction = ledgerAccountsService.findByAccountIdAndCustomQuery(id, startDate, endDate, requestParameter);
		
		
		
		return ResponseEntity.ok(ledgerEntries);	
	
	}
	
	@GetMapping("/superParent/{id}")
	public ResponseEntity<Page<LedgerEntries>> findBySuperParent(@PathVariable Long id, RequestParameter requestParameter, HttpSession session){

		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if (!accessPermission.getReadProfitLossStatementSettings()) {
			return ResponseEntity.badRequest().build();
		}
	

		return ResponseEntity.ok(ledgerEntriesService.findBySuperParent(id, requestParameter));
	}
	
	@GetMapping("/transactionType/{id}")
	public ResponseEntity<Page<LedgerEntries>> findByTransactionType(@PathVariable Long id, RequestParameter requestParameter, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if (!accessPermission.getReadProfitLossStatementSettings()) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(ledgerEntriesService.findByTransactionType(id, requestParameter));
	}
}

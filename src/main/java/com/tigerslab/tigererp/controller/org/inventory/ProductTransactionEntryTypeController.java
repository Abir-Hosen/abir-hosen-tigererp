package com.tigerslab.tigererp.controller.org.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.inventory.ProductTransactionEntryType;
import com.tigerslab.tigererp.service.org.inventory.ProductTransactionEntryTypeService;

@RestController
@RequestMapping("/api/productTransactionEntryType")
public class ProductTransactionEntryTypeController {

	@Autowired
	ProductTransactionEntryTypeService service;
	
	private Logger logger = LoggerFactory.getLogger(ProductTransactionEntryTypeController.class);
	

	@GetMapping
	public ResponseEntity<Page<ProductTransactionEntryType>> findAllBySortAndOrder(RequestParameter requestParameter) {
		logger.info("Entering GET method");
		Page<ProductTransactionEntryType> page = service.findAllBySortAndOrder(requestParameter);
		return ResponseEntity.ok(page);
	}
	
}

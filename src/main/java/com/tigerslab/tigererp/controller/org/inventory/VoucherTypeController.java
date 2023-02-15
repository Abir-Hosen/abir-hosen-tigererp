package com.tigerslab.tigererp.controller.org.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tigerslab.tigererp.model.financial.VoucherType;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.service.financial.VoucherTypeService;

@RestController
@RequestMapping("/api/voucherType")
public class VoucherTypeController {

	@Autowired
	VoucherTypeService service;

	private Logger logger = LoggerFactory.getLogger(VoucherTypeController.class);

	@GetMapping
	public ResponseEntity<Page<VoucherType>> findAllBySortAndOrder(RequestParameter requestParameter) {
		logger.info("Entering GET method");
		Page<VoucherType> page = service.findAllBySortAndOrder(requestParameter);
		return ResponseEntity.ok(page);
	}

}

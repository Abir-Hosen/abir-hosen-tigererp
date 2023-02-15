package com.tigerslab.tigererp.controller.org;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tigerslab.tigererp.model.financial.AccountEquation;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.service.financial.AccountEquationService;

@RestController
@RequestMapping("/api/accountequation")
public class AccountEquationController {
	
	@Autowired
	private AccountEquationService service;
	
	private Logger logger = LoggerFactory.getLogger(AccountEquationController.class);
	
	@GetMapping
	public ResponseEntity<Page<AccountEquation>> findAllBySortAndOrder(RequestParameter requestParameter) {
		logger.info("Entering GET method");
		Page<AccountEquation> page = service.findAllBySortAndOrder(requestParameter);
		return ResponseEntity.ok(page);
	}

}

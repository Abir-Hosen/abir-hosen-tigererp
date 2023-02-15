package com.tigerslab.tigererp.controller.org.inventory;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.order.Batch;
import com.tigerslab.tigererp.service.org.inventory.BatchService;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

	private Logger logger = LoggerFactory.getLogger(BatchController.class);
	
	@Autowired
	BatchService service;
	

	@GetMapping
	public ResponseEntity<Page<Batch>> findAllBySortAndOrder(RequestParameter requestParameter) {
		logger.info("Entering GET method");
		Page<Batch> page = service.findAll(requestParameter);
		return ResponseEntity.ok(page);
	}
	@PostMapping
	public ResponseEntity<Batch> create(@RequestBody @Valid Batch batch, BindingResult bindingResult) {
		logger.info("Entering POST Method");
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		else {
			service.save(batch);
			return ResponseEntity.ok(batch);
		}
	}
}

package com.tigerslab.tigererp.controller.org;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.httphelper.ResponseMetadata;
import com.tigerslab.tigererp.model.inventory.StockItem;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.service.org.inventory.StockItemService;

@RestController
@RequestMapping("/api/stockItem")
public class StockItemController {

	@Autowired
	private StockItemService service;
	@Autowired
	AccessPermission accessPermission;
	
	private Logger logger = LoggerFactory.getLogger(StockItemController.class);
	
	@GetMapping
	public ResponseEntity<Page<StockItem>> findAllBySortAndOrder(RequestParameter requestParameter, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadStockItemSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET method");
		Page<StockItem> page = service.findAllBySortAndOrder(requestParameter);
		return ResponseEntity.ok(page);
	}
	
	@PostMapping
	public ResponseEntity<StockItem> create(@RequestBody @Valid StockItem stockItem, BindingResult bindingResult, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getCreateStockItemSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering POST Method");
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		else {
			service.save(stockItem);
			return ResponseEntity.ok(stockItem);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StockItem> findById(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadStockItemSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET findById");
		Optional<StockItem> stockItem = service.findById(id);
		if(!stockItem.isPresent()) {
			logger.info("Id "+id+" is not existed");
			ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(stockItem.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<StockItem> update(@PathVariable Long id, @RequestBody @Valid StockItem stockItem, BindingResult bindingResult, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateStockItemSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering PUT Method");
		if(!service.findById(id).isPresent()){
			return ResponseEntity.badRequest().build();
		}
		if(!bindingResult.hasErrors()) {
			return ResponseEntity.ok(service.save(stockItem));
		}
		else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteStockItemSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering DELETE Method");
		ResponseMetadata metaData = new ResponseMetadata(ConstantFactory.OK);
		service.deleteById(id);
		return ResponseEntity.ok(metaData);
	}
}

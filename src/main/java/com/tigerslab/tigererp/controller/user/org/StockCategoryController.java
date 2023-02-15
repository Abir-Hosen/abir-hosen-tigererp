package com.tigerslab.tigererp.controller.user.org;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import com.tigerslab.tigererp.model.org.Branch;
import com.tigerslab.tigererp.model.org.StockCategory;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.service.org.BranchService;
import com.tigerslab.tigererp.service.org.StockCategoryService;

@RestController
@RequestMapping("/api/stockcategory")
public class StockCategoryController {
	
	@Autowired
	private StockCategoryService stockCategoryService;

	@Autowired
	AccessPermission accessPermission;
	
	private Logger logger = LoggerFactory.getLogger(StockCategoryController.class);
	
	@GetMapping
	public ResponseEntity<Page<StockCategory>> findAllBySortAndOrder(RequestParameter requestParameter, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadStockCategory()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET method");
		Page<StockCategory> page = stockCategoryService.findAllBySortAndOrder(requestParameter);
		return ResponseEntity.ok(page);
	}
	
	@PostMapping
	public ResponseEntity<StockCategory> create(@RequestBody @Valid StockCategory stockCategory, BindingResult bindingResult, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getCreateStockCategory()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering POST Method");
		StringBuilder serverSideErrors = new StringBuilder("");
		if(bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error : errorList) {
				serverSideErrors.append(", ").append(error.getDefaultMessage());
			}
			logger.error("Form has errors: "+serverSideErrors);
			return ResponseEntity.badRequest().build();
		}
		else {
			stockCategoryService.save(stockCategory);
			return ResponseEntity.ok(stockCategory);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<StockCategory> findById(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadStockCategory()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET findById");
		Optional<StockCategory> stockCategory = stockCategoryService.findById(id);
		if(!stockCategory.isPresent()) {
			logger.info("Id "+id+" is not existed");
			ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(stockCategory.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<StockCategory> update(@PathVariable Long id, @RequestBody @Valid StockCategory stockCategory, BindingResult bindingResult, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateStockCategory()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering PUT Method");
		if(!stockCategoryService.findById(id).isPresent()){
			return ResponseEntity.badRequest().build();
		}
		Optional<StockCategory> getStockCategory = stockCategoryService.findById(id);
		if(getStockCategory.get() != null) {
			return ResponseEntity.ok(stockCategoryService.save(stockCategory));
		}
		else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteStockCategory()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering DELETE Method");
		ResponseMetadata metaData = new ResponseMetadata(ConstantFactory.OK);
		
		Optional<StockCategory> getCategory =  stockCategoryService.findById(id);
		
		if(!stockCategoryService.findById(id).isPresent()){
			logger.info("Id "+id+" is not existed");
			return ResponseEntity.badRequest().build();
		}
		if(getCategory.get().getParentId() == 0) {
			List<StockCategory> stockCatList = stockCategoryService.findByParentId(id);
			for(StockCategory s : stockCatList) {
				stockCategoryService.deleteById(s.getId());
			}
			stockCategoryService.deleteById(id);
			return ResponseEntity.ok(metaData);
		}
		else {
			stockCategoryService.deleteById(id);
			return ResponseEntity.ok(metaData);
		}

	}

}

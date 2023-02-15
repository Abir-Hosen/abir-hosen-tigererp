package com.tigerslab.tigererp.controller.user.org;

import java.util.List;
import java.util.Optional;

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

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.PaperType;
import com.tigerslab.tigererp.service.org.PaperTypeService;

@RestController
@RequestMapping("/api/paperType")
public class PaperTypeController {
	
	@Autowired
	private PaperTypeService paperTypeService;
	
	private Logger logger = LoggerFactory.getLogger(PaperTypeController.class);
	
	@GetMapping
	public ResponseEntity<Page<PaperType>> findAllBySortAndOrder(RequestParameter requestParameter) {
		logger.info("Entering GET method");
		Page<PaperType> page = paperTypeService.findAllBySortAndOrder(requestParameter);
		return ResponseEntity.ok(page);
	}
	
	@PostMapping
	public ResponseEntity<PaperType> create(@RequestBody @Valid PaperType paperType, BindingResult bindingResult) {
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
			paperTypeService.save(paperType);
			return ResponseEntity.ok(paperType);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PaperType> findById(@PathVariable Long id){
		logger.info("Entering GET findById");
		Optional<PaperType> SinglePaperTypee = paperTypeService.findById(id);
		if(!SinglePaperTypee.isPresent()) {
			logger.info("Id "+id+" is not existed");
			ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(SinglePaperTypee.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<PaperType> update(@PathVariable Long id, @RequestBody @Valid PaperType paperType, BindingResult bindingResult){
		
		logger.info("Entering PUT Method");
		logger.info("PaperType Info: "+paperType);
		StringBuilder serverSideErrors = new StringBuilder("");
		if(bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error : errorList) {
				serverSideErrors.append(", ").append(error.getDefaultMessage());
			}
			logger.error("Form has errors: "+serverSideErrors);
			return ResponseEntity.badRequest().build();
		}else {
			if(!paperTypeService.findById(id).isPresent()) {
				logger.info("Id "+id+" is not existed");
				return ResponseEntity.badRequest().build();
			}else {
				paperTypeService.save(paperType);
				return ResponseEntity.ok(paperType);
			}
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PaperType> delete(@PathVariable Long id){
		logger.info("Entering DELETE Method");
		if(!paperTypeService.findById(id).isPresent()){
			logger.info("Id "+id+" is not existed");
			return ResponseEntity.badRequest().build();
		}
		paperTypeService.deleteById(id);
		
		return ResponseEntity.ok(null);
	}

}

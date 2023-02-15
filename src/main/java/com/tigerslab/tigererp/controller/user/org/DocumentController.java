package com.tigerslab.tigererp.controller.user.org;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tigerslab.tigererp.controller.FileUploadUtility;
import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.Document;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.service.org.DocumentService;

@Transactional
@RestController
@RequestMapping("/api/document")
public class DocumentController{
	
	@Autowired
	private DocumentService service;
	
	@Autowired
    private ServletContext servletContext;

	@Autowired
	AccessPermission accessPermission;
	
	private Logger logger = LoggerFactory.getLogger(DocumentController.class);
	
	@GetMapping
	public ResponseEntity<Page<Document>> findAllBySortAndOrder(RequestParameter requestParameter, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadMediaAndDocSettings()) {
			return ResponseEntity.badRequest().build();
		}
//		System.out.println(servletContext.getRealPath("/resource/uploads"));
		logger.info("Entering GET method");
		Page<Document> page = service.findAllBySortAndOrder(requestParameter);
		return ResponseEntity.ok(page);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Document> findById(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadMediaAndDocSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET findById");
		Optional<Document> Singledocumente = service.findById(id);
		if(!Singledocumente.isPresent()) {
			logger.info("Id "+id+" is not existed");
			ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(Singledocumente.get());
	}
	
	@PostMapping(headers = "Content-Type= multipart/form-data")
	public ResponseEntity<Document> create(@RequestParam("file") MultipartFile file, HttpSession httpSession, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getCreateMediaAndDocSettings()) {
			return ResponseEntity.badRequest().build();
		}
		
		System.out.println("WORKING ORIGINL NAME is --- "+file.getOriginalFilename());
		//System.out.println(document.getFile().getOriginalFilename());
		
		if(!file.getOriginalFilename().equals("")) {
			FileUploadUtility.uploadFile(httpSession, file, "logo");
		}
		
			Document doc = new Document();
			doc.setDocumentName(doc.getDocumentName());
			doc.setDocumentFormalName(doc.getDocumentFormalName());
			doc.setDocumentFullPath(ConstantFactory.UPLOAD_PATH);
			
			service.save(doc);
			return ResponseEntity.ok(null);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Document> update(@PathVariable Long id, @RequestBody @Valid Document document, BindingResult bindingResult, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateMediaAndDocSettings()) {
			return ResponseEntity.badRequest().build();
		}

		logger.info("Entering PUT Method");
		logger.info("document Info: "+document);

		if(!document.getFile().getOriginalFilename().equals("")) {
			FileUploadUtility.uploadFile(session, document.getFile(), document.getDocumentFormalName());
		}
		
		StringBuilder serverSideErrors = new StringBuilder("");
		if(bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error : errorList) {
				serverSideErrors.append(", ").append(error.getDefaultMessage());
			}
			logger.error("Form has errors: "+serverSideErrors);
			return ResponseEntity.badRequest().build();
		}else {
			if(!service.findById(id).isPresent()) {
				logger.info("Id "+id+" is not existed");
				return ResponseEntity.badRequest().build();
			}else {
				service.save(document);
				return ResponseEntity.ok(document);
			}
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Document> delete(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteMediaAndDocSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering DELETE Method");
		if(!service.findById(id).isPresent()){
			logger.info("Id "+id+" is not existed");
			return ResponseEntity.badRequest().build();
		}
		service.deleteById(id);
		
		return ResponseEntity.ok(null);
	}

}

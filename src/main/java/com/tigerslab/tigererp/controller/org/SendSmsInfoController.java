package com.tigerslab.tigererp.controller.org;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.sms.SendSMSInfo;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.service.sms.SendSMSInfoService;

@RestController
@RequestMapping("/api/sendsms")
public class SendSmsInfoController {
	
	@Autowired
	private SendSMSInfoService service;
	@Autowired
	AccessPermission accessPermission;
	
	private Logger logger = LoggerFactory.getLogger(SendSmsInfoController.class);
	
	@GetMapping
	public ResponseEntity<Page<SendSMSInfo>> findAllBySortAndOrder(RequestParameter requestParameter, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadSmsSettings()) {
			return ResponseEntity.badRequest().build();
		}
		
		logger.info("Entering GET method");
		Page<SendSMSInfo> page = (Page<SendSMSInfo>) service.findAllBySortAndOrder(requestParameter);
		return ResponseEntity.ok(page);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<SendSMSInfo> findById(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadSmsSettings()) {
			return ResponseEntity.badRequest().build();
		}
		
		logger.info("Entering GET findById");
		Optional<SendSMSInfo> sendSMSInfo = (Optional<SendSMSInfo>) service.findById(id);
		if(!sendSMSInfo.isPresent()) {
			logger.info("Id "+id+" is not existed");
			ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(sendSMSInfo.get());
	}
}

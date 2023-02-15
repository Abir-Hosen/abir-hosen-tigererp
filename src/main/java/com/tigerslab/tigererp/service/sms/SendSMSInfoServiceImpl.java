package com.tigerslab.tigererp.service.sms;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.sms.SendSMSInfo;
import com.tigerslab.tigererp.repository.sms.SmsSendInfoRepository;

@Service("sendSMSInfoService")
public class SendSMSInfoServiceImpl implements SendSMSInfoService {
	
	Logger logger = LoggerFactory.getLogger(SendSMSInfoService.class);
	
	@Autowired
	private SmsSendInfoRepository smsSendInfoRepository;

	private Pageable pagable;
	
	@Override
	public Page<?> findAllBySortAndOrder(RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return smsSendInfoRepository.findAllBySortAndOrder(requestParameter.getFilter(), pagable);
	}

	@Override
	public Optional<?> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object save(Object object) {
		return smsSendInfoRepository.save((SendSMSInfo)object);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

}

package com.tigerslab.tigererp.service.org;

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
import com.tigerslab.tigererp.model.org.Company;
import com.tigerslab.tigererp.repository.org.CompanyProfileRepository;
import com.tigerslab.tigererp.service.user.CrudInterface;

@Service("companyProfileService")
public class CompanyProfileService implements CrudInterface{

	Logger logger = LoggerFactory.getLogger(CompanyProfileService.class);
	
	@Autowired
	CompanyProfileRepository companyProfileRepository;
	
	private Pageable pagable;

	@Override
	public Page<Company> findAllBySortAndOrder(RequestParameter requestParameter) {
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
		return companyProfileRepository.findAllBySortAndOrder(requestParameter.getFilter(), pagable);
	}

	@Override
	public Optional<Company> findById(Long id) {
		return companyProfileRepository.findById(id);
	}

	@Override
	public Object save(Object object) {
		companyProfileRepository.save((Company)object);
		return (Company)object;
	}

	@Override
	public void deleteById(Long id) {
		companyProfileRepository.deleteById(id);
	}

	@Override
	public void delete(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		companyProfileRepository.deleteAll();
		
	}

}

package com.tigerslab.tigererp.service.financial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tigerslab.tigererp.model.financial.AccountEquation;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.repository.financial.AccountEquationRepository;

@Service("accountEquationService")
public class AccountEquationServiceImpl implements AccountEquationService {

	Logger logger = LoggerFactory.getLogger(AccountChartService.class);

	@Autowired
	private AccountEquationRepository accountEquationRepository;

	private Pageable pagable;

	@Override
	public Page<AccountEquation> findAllBySortAndOrder(RequestParameter requestParameter) {
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
		return accountEquationRepository.findAllBySortAndOrder(requestParameter.getFilter(), pagable);
	}

	@Override
	public AccountEquation save(AccountEquation accountEquation) {
		// TODO Auto-generated method stub
		return accountEquationRepository.save(accountEquation);
	}

}

package com.tigerslab.tigererp.service.financial;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tigerslab.tigererp.model.financial.AccountChart;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.repository.financial.AccountChartRepository;
import com.tigerslab.tigererp.repository.org.BranchRepository;
import com.tigerslab.tigererp.service.org.BranchService;

@Service("accountChartService")
public class AccountChartServiceImpl implements AccountChartService {

	Logger logger = LoggerFactory.getLogger(AccountChartService.class);

	@Autowired
	private AccountChartRepository accountChartRepository;

	private Pageable pagable;
	
	@Override
	public Page<AccountChart> findAllBySortAndOrder(RequestParameter requestParameter) {
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
		return accountChartRepository.findAllBySortAndOrder(requestParameter.getFilter(), pagable);
	}

	@Override
	public Optional<AccountChart> findById(Long id) {
		return accountChartRepository.findById(id);
	}

	@Override
	public AccountChart findByName(String name) {
		return accountChartRepository.findByName(name);
	}

	@Override
	public AccountChart save(AccountChart accountChart) {
		return accountChartRepository.save(accountChart);
	}

	@Override
	public void deleteById(Long id) {
		accountChartRepository.deleteById(id);
	}

}

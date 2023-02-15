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
import com.tigerslab.tigererp.model.financial.TransactionEntryType;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.repository.financial.AccountChartRepository;
import com.tigerslab.tigererp.repository.financial.TransactionEntryTypeRepository;
import com.tigerslab.tigererp.repository.org.BranchRepository;
import com.tigerslab.tigererp.service.org.BranchService;

@Service("transactionEntryTypeService")
public class TransactionEntryTypeServiceImpl implements TransactionEntryTypeService {

	Logger logger = LoggerFactory.getLogger(AccountChartService.class);

	@Autowired
	private TransactionEntryTypeRepository transactionEntryTypeRepository;

	private Pageable pagable;
	
	@Override
	public Page<TransactionEntryType> findAllBySortAndOrder(RequestParameter requestParameter) {
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
		return transactionEntryTypeRepository.findAllBySortAndOrder(requestParameter.getFilter(), pagable);
	}

	@Override
	public Optional<TransactionEntryType> findById(Long id) {
		return transactionEntryTypeRepository.findById(id);
	}

	@Override
	public TransactionEntryType findByName(String name) {
		return transactionEntryTypeRepository.findByName(name);
	}

	@Override
	public TransactionEntryType save(TransactionEntryType transactionEntryType) {
		// TODO Auto-generated method stub
		return transactionEntryTypeRepository.save(transactionEntryType);
	}

}

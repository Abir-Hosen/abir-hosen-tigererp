package com.tigerslab.tigererp.service.financial;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.financial.AccountChart;
import com.tigerslab.tigererp.model.financial.TransactionEntryType;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;

public interface TransactionEntryTypeService {
	
	public Page<TransactionEntryType> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public Optional<TransactionEntryType> findById(Long id);
	
	public TransactionEntryType save(TransactionEntryType transactionEntryType);
	
	public TransactionEntryType findByName(String name);

}

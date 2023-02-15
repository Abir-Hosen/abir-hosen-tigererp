package com.tigerslab.tigererp.service.org;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.financial.LedgerGroup;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;

public interface LedgerGroupService {
	
	public Page<LedgerGroup> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public Optional<LedgerGroup> findById(Long id);
	
	public Optional<LedgerGroup> findByName(String name);
	
	public LedgerGroup save(LedgerGroup ledgerGroup);

	public void deleteById(Long id);
}

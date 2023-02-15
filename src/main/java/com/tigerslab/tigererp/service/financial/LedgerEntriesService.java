package com.tigerslab.tigererp.service.financial;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.financial.LedgerEntries;
import com.tigerslab.tigererp.model.financial.VoucherType;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;

public interface LedgerEntriesService {
	
	public Page<LedgerEntries> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public Page<LedgerEntries> findByAccountIdAndCustomQuery(Long id, RequestParameter requestParameter);
	
	public Page<LedgerEntries> findByVoucher(Long id, Date startDate, Date endDate,  RequestParameter requestParameter);

	Page<LedgerEntries> findByVoucherType(Date startDate, Date endDate, RequestParameter requestParameter);
	
	Page<LedgerEntries> findBySuperParent(Long id, RequestParameter requestParameter);
	
	Page<LedgerEntries> findBySuperParentByDate(Long id, Date startDate, Date endDate, RequestParameter requestParameter);
	
	Page<LedgerEntries> findByTransactionType(Long id, RequestParameter requestParameter);
	
	public Optional<LedgerEntries> findById(Long id);
	
	public LedgerEntries findByName(String name);
	
	public LedgerEntries save(LedgerEntries ledgerEntries);

	public void deleteById(Long id);

}

package com.tigerslab.tigererp.service.financial;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.financial.LedgerAccounts;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;

public interface LedgerAccountsService {
	
	public Page<LedgerAccounts> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public Page<LedgerAccounts> findAllLedgerAccountsWithBalance(RequestParameter requestParameter);

	public Page<LedgerAccounts> findByAccountIdAndCustomQuery(Long id, Date startDate, Date endDate, RequestParameter requestParameter);

	public Optional<LedgerAccounts> findById(Long id);

	public Long findLedgerAccountsByParentId(Long id);
	
	public Page<LedgerAccounts> findLedgerAccountsBySuperParentId(Long id, RequestParameter requestParameter);
	
	public LedgerAccounts findByName(String name);
	
	public LedgerAccounts save(LedgerAccounts ledgerAccounts);
	
	public LedgerAccounts saveWithTransactions(LedgerAccounts ledgerAccounts);

	public void deleteById(Long id);

}

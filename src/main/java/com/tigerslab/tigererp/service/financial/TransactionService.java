package com.tigerslab.tigererp.service.financial;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.financial.LedgerAccounts;
import com.tigerslab.tigererp.model.financial.LedgerEntries;
import com.tigerslab.tigererp.model.financial.Transaction;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;

public interface TransactionService {
	
	public Page<Transaction> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public Optional<Transaction> findById(Long id);
	
	public Transaction findByName(String name);
	
	public Transaction save(Transaction ledgerAccounts);

	public void deleteById(Long id);
	
	public void doubleEntryTransaction(LedgerAccounts account, Transaction transaction);
	
	public void makeDoubleEntryTransaction(LedgerEntries [] ledgerEntries);

}

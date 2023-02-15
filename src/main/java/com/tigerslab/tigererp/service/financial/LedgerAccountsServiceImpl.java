package com.tigerslab.tigererp.service.financial;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.financial.LedgerAccounts;
import com.tigerslab.tigererp.model.financial.Transaction;
import com.tigerslab.tigererp.model.financial.TransactionEntryType;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.repository.financial.AccountChartRepository;
import com.tigerslab.tigererp.repository.financial.LedgerAccountsRepository;

@Service("ledgerAccountsService")
public class LedgerAccountsServiceImpl implements LedgerAccountsService {

	Logger logger = LoggerFactory.getLogger(LedgerAccountsService.class);

	@Autowired
	private LedgerAccountsRepository repository;
	
	@Autowired
	private TransactionService transactionService;
	

	private Pageable pagable;

	@Override
	public Page<LedgerAccounts> findAllBySortAndOrder(RequestParameter requestParameter) {
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
		return repository.findAllBySortAndOrder(requestParameter.getFilter(), pagable);
	}

	@Override
	public Page<LedgerAccounts> findAllLedgerAccountsWithBalance(RequestParameter requestParameter) {
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
		return repository.findAllLedgerAccountsWithBalance(pagable);
	}

	@Override
	public Long findLedgerAccountsByParentId(Long id) {
		// TODO Auto-generated method stub
		return repository.findLedgerAccountsByParentId(id);
	}
	
	@Override
	public Page<LedgerAccounts> findLedgerAccountsBySuperParentId(Long id, RequestParameter requestParameter) {
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
		return repository.findLedgerAccountsBySuperParentId(id, pagable);
	}

	@Override
	public Page<LedgerAccounts> findByAccountIdAndCustomQuery(Long id, Date startDate, Date endDate,   RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			System.out.println(sortOrder);
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findByLedgerAccountsIdWithBalance(id,  startDate, endDate, pagable);
	}

	@Override
	public Optional<LedgerAccounts> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public LedgerAccounts findByName(String accountName) {
		return repository.findByAccountName(accountName);
	}

	@Override
	@Transactional
	public LedgerAccounts save(LedgerAccounts ledgerAccounts) {
		LedgerAccounts account = repository.save(ledgerAccounts);
		return account;
	}
	
	@Override
	@Transactional
	public LedgerAccounts saveWithTransactions(LedgerAccounts ledgerAccounts) {
		System.out.println("------ Save ledger start -----");
		LedgerAccounts newLedgerAccount = this.save(ledgerAccounts);
		Transaction transaction = new Transaction();
		transaction.setDate(newLedgerAccount.getCreationDate());
		transaction.setDescription("Begining Balance");
		transaction.setAmount(newLedgerAccount.getOpeningBalance());
		System.out.println("------ NEW ACC CREATED -----");
		transactionService.doubleEntryTransaction(newLedgerAccount, transaction);
		return newLedgerAccount;
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}

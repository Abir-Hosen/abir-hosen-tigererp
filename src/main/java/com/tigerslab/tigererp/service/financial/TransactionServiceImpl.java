package com.tigerslab.tigererp.service.financial;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.tigerslab.tigererp.model.financial.AccountChart;
import com.tigerslab.tigererp.model.financial.LedgerAccounts;
import com.tigerslab.tigererp.model.financial.LedgerEntries;
import com.tigerslab.tigererp.model.financial.Transaction;
import com.tigerslab.tigererp.model.financial.TransactionEntryType;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.repository.financial.AccountChartRepository;
import com.tigerslab.tigererp.repository.financial.LedgerAccountsRepository;
import com.tigerslab.tigererp.repository.financial.TransactionRepository;

@Service("transactionService")
@Transactional
public class TransactionServiceImpl implements TransactionService {

	Logger logger = LoggerFactory.getLogger(LedgerAccountsService.class);

	@Autowired
	private TransactionRepository repository;
	
	@Autowired
	private TransactionEntryTypeService transactionEntryTypeService;
	
	@Autowired
	private LedgerAccountsService ledgerAccountsService;
	
	@Autowired
	private LedgerEntriesService ledgerEntriesService;
	

	private Pageable pagable;

	@Override
	public Page<Transaction> findAllBySortAndOrder(RequestParameter requestParameter) {
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
	public Optional<Transaction> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Transaction findByName(String date) {
		return repository.findByDate(date);
	}

	@Override
	public Transaction save(Transaction transaction) {
		return repository.save(transaction);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	@Transactional
	@Override
	public void doubleEntryTransaction(LedgerAccounts account, Transaction transaction) {
		TransactionEntryType tranTypeDebit = transactionEntryTypeService.findByName(ConstantFactory.DEBIT);
		TransactionEntryType tranTypeCredit = transactionEntryTypeService.findByName(ConstantFactory.CREDIT);
		
		LedgerEntries ledgerEntriesCredit = new LedgerEntries();
		LedgerEntries ledgerEntriesDebit = new LedgerEntries();
		
		Transaction transaction1 =  new Transaction();
		Transaction transaction2 =  new Transaction();
		
		if(account.getParentAccount().getParentAccount().getName().equals(ConstantFactory.LIABILITIES) || account.getParentAccount().getParentAccount().getName().equals(ConstantFactory.EQUITY) || account.getParentAccount().getParentAccount().getName().equals(ConstantFactory.REVENUE)) {

			if(account.getOpeningBalance().compareTo(BigDecimal.valueOf(0)) < 0 || account.getOpeningBalance().compareTo(BigDecimal.valueOf(0)) == 0) {
				
				transaction1.setAmount(transaction.getAmount());
				transaction1.setDescription("Opening Balance");
				
				ledgerEntriesDebit.setLedgerAccounts(account);
				ledgerEntriesDebit.setTransactions(transaction1);
				ledgerEntriesDebit.setTransactionEntryType(tranTypeDebit);
				ledgerEntriesDebit = ledgerEntriesService.save(ledgerEntriesDebit);
				
				LedgerAccounts creditAccountFetch = ledgerAccountsService.findByName(ConstantFactory.CAPITAL);
				transaction2.setAmount(transaction.getAmount());
				transaction2.setDescription("Created for opening balance adjustment for an account. Ref. Account ID: "+account.getId());
				ledgerEntriesCredit.setLedgerAccounts(creditAccountFetch);
				ledgerEntriesCredit.setTransactions(transaction2);
				ledgerEntriesCredit.setTransactionEntryType(tranTypeCredit);
				ledgerEntriesCredit = ledgerEntriesService.save(ledgerEntriesCredit);
			}
			else {
				
				transaction1.setAmount(transaction.getAmount());
				transaction1.setDescription("Created for opening balance adjustment for an account. Ref. Account ID: "+account.getId());
				
				LedgerAccounts debitAccountFetch = ledgerAccountsService.findByName(ConstantFactory.CAPITAL);
				ledgerEntriesDebit.setLedgerAccounts(debitAccountFetch);
				ledgerEntriesDebit.setTransactions(transaction1);
				ledgerEntriesDebit.setTransactionEntryType(tranTypeDebit);
				ledgerEntriesDebit = ledgerEntriesService.save(ledgerEntriesDebit);
				
				transaction2.setAmount(transaction.getAmount());
				transaction2.setDescription("Opening Balance");
				ledgerEntriesCredit.setLedgerAccounts(account);
				ledgerEntriesCredit.setTransactions(transaction2);
				ledgerEntriesCredit.setTransactionEntryType(tranTypeCredit);
				ledgerEntriesCredit = ledgerEntriesService.save(ledgerEntriesCredit);
			}
			
		}// end parent if
		if(account.getParentAccount().getParentAccount().getName().equals(ConstantFactory.ASSETS) || account.getParentAccount().getParentAccount().getName().equals(ConstantFactory.EXPENSES)) {
			if(account.getOpeningBalance().compareTo(BigDecimal.valueOf(0)) < 0 || account.getOpeningBalance().compareTo(BigDecimal.valueOf(0)) == 0) {
				
				LedgerAccounts debitAccountFetch = ledgerAccountsService.findByName(ConstantFactory.CAPITAL);
				transaction1.setAmount(transaction.getAmount().multiply(BigDecimal.valueOf(-1)));
				transaction1.setDescription("Created for opening balance adjustment of account. Account ID: "+account.getId());
				
				ledgerEntriesDebit.setLedgerAccounts(debitAccountFetch);
				ledgerEntriesDebit.setTransactions(transaction1);
				ledgerEntriesDebit.setTransactionEntryType(tranTypeDebit);
				ledgerEntriesDebit = ledgerEntriesService.save(ledgerEntriesDebit);
				
				transaction2.setAmount(transaction.getAmount());
				transaction2.setDescription("Opening Balance");
				ledgerEntriesCredit.setLedgerAccounts(account);
				ledgerEntriesCredit.setTransactions(transaction2);
				ledgerEntriesCredit.setTransactionEntryType(tranTypeCredit);
				ledgerEntriesCredit = ledgerEntriesService.save(ledgerEntriesCredit);
			}
			else {
				transaction1.setAmount(transaction.getAmount());
				transaction1.setDescription("Opening Balance");
				
				ledgerEntriesDebit.setLedgerAccounts(account);
				ledgerEntriesDebit.setTransactions(transaction1);
				ledgerEntriesDebit.setTransactionEntryType(tranTypeDebit);
				ledgerEntriesDebit = ledgerEntriesService.save(ledgerEntriesDebit);
				
				transaction2.setAmount(transaction.getAmount().multiply(BigDecimal.valueOf(-1)));
				transaction2.setDescription("Created for opening balance adjustment of account. Account ID: "+account.getId());
				LedgerAccounts creditAccountFetch = ledgerAccountsService.findByName(ConstantFactory.CAPITAL);
				ledgerEntriesCredit.setLedgerAccounts(creditAccountFetch);
				ledgerEntriesCredit.setTransactions(transaction2);
				ledgerEntriesCredit.setTransactionEntryType(tranTypeCredit);
				ledgerEntriesCredit = ledgerEntriesService.save(ledgerEntriesCredit);
				
			}
			
		}// end else
		
	}
	
	@Override
	@Transactional
	public void makeDoubleEntryTransaction(LedgerEntries [] ledgerEntries) {
		
		TransactionEntryType tranTypeDebit = transactionEntryTypeService.findByName(ConstantFactory.DEBIT);
		TransactionEntryType tranTypeCredit = transactionEntryTypeService.findByName(ConstantFactory.CREDIT);
		
		LedgerEntries ledgerEntriesDebit = new LedgerEntries();
		LedgerEntries ledgerEntriesCredit = new LedgerEntries();
		
		ledgerEntriesDebit.setOrder(ledgerEntries[0].getOrder());
		ledgerEntriesCredit.setOrder(ledgerEntries[1].getOrder());
		
		Transaction transaction1 = new Transaction();
		Transaction transaction2 = new Transaction();
		
		for(int i = 0 ; i<2; i++) {
			if(i == 0) {
				ledgerEntries[i].setTransactionEntryType(tranTypeDebit);
				ledgerEntriesDebit.setTransactionEntryType(tranTypeDebit);
				transaction1 = ledgerEntries[i].getTransactions();
				ledgerEntriesDebit.setSecondLedgerAccounts(ledgerEntries[i].getSecondLedgerAccounts());
				ledgerEntriesDebit.setVoucherType(ledgerEntries[i].getVoucherType());
				ledgerEntriesDebit.setTransactions(transaction1);
				ledgerEntriesDebit.setCreatedBy(ledgerEntries[i].getCreatedBy());
			}
			else if(i == 1) {
				ledgerEntries[i].setTransactionEntryType(tranTypeCredit);
				ledgerEntriesCredit.setTransactionEntryType(tranTypeCredit);
				transaction2 = ledgerEntries[i].getTransactions();
				ledgerEntriesCredit.setVoucherType(ledgerEntries[i].getVoucherType());
				ledgerEntriesCredit.setTransactions(transaction2);
				ledgerEntriesCredit.setCreatedBy(ledgerEntries[i].getCreatedBy());
			}
			
			if(ledgerEntries[i].getTransactions().getAmount().compareTo(BigDecimal.valueOf(0)) > 0) {
				
				if(ledgerEntries[i].getLedgerAccounts().getParentAccount().getParentAccount().getName().equals(ConstantFactory.LIABILITIES) || ledgerEntries[i].getLedgerAccounts().getParentAccount().getParentAccount().getName().equals(ConstantFactory.EQUITY) || ledgerEntries[i].getLedgerAccounts().getParentAccount().getParentAccount().getName().equals(ConstantFactory.REVENUE)) {
	
					if(ledgerEntries[i].getTransactionEntryType().getName().equals(ConstantFactory.DEBIT)) {
						//CHECKED
						BigDecimal amount = ledgerEntries[i].getTransactions().getAmount().multiply(BigDecimal.valueOf(-1));
						transaction1.setAmount(amount);
						ledgerEntriesDebit.setLedgerAccounts(ledgerEntries[i].getLedgerAccounts());					
						ledgerEntriesDebit.setTransactions(transaction1);
						ledgerEntriesDebit = ledgerEntriesService.save(ledgerEntriesDebit);
						
					}
					else if(ledgerEntries[i].getTransactionEntryType().getName().equals(ConstantFactory.CREDIT)){
						//CHECKED
						transaction2.setAmount(ledgerEntries[i].getTransactions().getAmount());
						ledgerEntriesCredit.setLedgerAccounts(ledgerEntries[i].getLedgerAccounts());
						ledgerEntriesCredit.setTransactions(transaction2);
						ledgerEntriesCredit = ledgerEntriesService.save(ledgerEntriesCredit);
					}
					
				}// end parent if
				if(ledgerEntries[i].getLedgerAccounts().getParentAccount().getParentAccount().getName().equals(ConstantFactory.ASSETS) || ledgerEntries[i].getLedgerAccounts().getParentAccount().getParentAccount().getName().equals(ConstantFactory.EXPENSES)) {
	
					if(ledgerEntries[i].getTransactionEntryType().getName().equals(ConstantFactory.DEBIT)) {
						//CHECKED
						transaction1.setAmount(ledgerEntries[i].getTransactions().getAmount());			
						ledgerEntriesDebit.setLedgerAccounts(ledgerEntries[i].getLedgerAccounts());
						ledgerEntriesDebit.setTransactions(transaction1);
						ledgerEntriesDebit = ledgerEntriesService.save(ledgerEntriesDebit);
					}
					else if(ledgerEntries[i].getTransactionEntryType().getName().equals(ConstantFactory.CREDIT)) {
						//CHECKED
						BigDecimal amount = ledgerEntries[i].getTransactions().getAmount().multiply(BigDecimal.valueOf(-1));
						transaction2.setAmount(amount);
						ledgerEntriesCredit.setTransactions(transaction2);
						ledgerEntriesCredit.setLedgerAccounts(ledgerEntries[i].getLedgerAccounts());
						ledgerEntriesCredit = ledgerEntriesService.save(ledgerEntriesCredit);
					}
					
				}// end else
			  }// End If parent
			} // end for loop
	} // end method

}

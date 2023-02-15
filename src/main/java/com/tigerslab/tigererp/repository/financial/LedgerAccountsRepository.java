package com.tigerslab.tigererp.repository.financial;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.financial.LedgerAccounts;

@Repository("ledgerAccountsRepository")
public interface LedgerAccountsRepository extends CrudRepository<LedgerAccounts, Long>{

	@Query("select a from LedgerAccounts a where a.accountName like %?1% or a.id like %?1%")
	Page<LedgerAccounts> findAllBySortAndOrder(String accountName, Pageable pageable);

	@Query("select l.transactions.originalDate, l.transactions.description, l.transactionEntryType.name, l.transactions.amount, SUM(l2.transactions.amount), l.id, l.order.id from LedgerAccounts a INNER JOIN LedgerEntries l On a.id=l.ledgerAccounts.id INNER JOIN LedgerEntries l2 On l2.id<=l.id AND l2.ledgerAccounts.id = a.id WHERE a.id = ?1  AND l.transactions.originalDate >=?2  AND l.transactions.originalDate <=?3 GROUP BY l.id, l.transactionEntryType.name, l.transactions.amount, l.transactions.originalDate, l.order.id ORDER BY l.id ASC") //AND  l2.voucherType.id=null     
	Page<LedgerAccounts> findByLedgerAccountsIdWithBalance(Long id, Date startDate, Date endDate, Pageable pageable);
	
	@Query("select a, SUM(l.transactions.amount) from LedgerAccounts a INNER JOIN LedgerEntries l On a.id=l.ledgerAccounts.id GROUP BY a ORDER BY a.id ASC")      
	Page<LedgerAccounts> findAllLedgerAccountsWithBalance(Pageable pageable);

	@Query("select SUM(l.transactions.amount) from LedgerAccounts a INNER JOIN LedgerEntries l On a.id=l.ledgerAccounts.id WHERE a.id=?1")
	BigDecimal findByLedgerAccountsWithBalance(long id);

	@Query("select COUNT(l) from LedgerAccounts l WHERE l.parentAccount.id=?1")
	Long findLedgerAccountsByParentId(Long id);
	
	@Query("select l from LedgerAccounts l WHERE l.parentAccount.parentAccount.id=?1")
	Page<LedgerAccounts> findLedgerAccountsBySuperParentId(Long id, Pageable pageable);
	
	LedgerAccounts findByAccountName(String accountName);

}

package com.tigerslab.tigererp.repository.financial;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.financial.AccountChart;
import com.tigerslab.tigererp.model.financial.LedgerAccounts;
import com.tigerslab.tigererp.model.financial.LedgerEntries;
import com.tigerslab.tigererp.model.financial.Transaction;
import com.tigerslab.tigererp.model.financial.VoucherType;

@Repository("ledgerEntriesRepository")
public interface LedgerEntriesRepository extends PagingAndSortingRepository<LedgerEntries, Long>{
	
	@Query("select a from LedgerEntries a where a.id like %?1% or a.id like %?1%")
	Page<LedgerEntries> findAllBySortAndOrder(String id, Pageable pageable);
	
	@Query("select l from LedgerEntries l INNER JOIN l.ledgerAccounts a INNER JOIN l.transactions t WHERE a.id = ?1")
	Page<LedgerEntries> findByLedgerAccountsId(Long id, Pageable pageable);
	
	List<LedgerEntries> findByledgerAccountsId(Long id);
	
//	@Query("SELECT le1 FROM LedgerEntries le1 INNER JOIN LedgerEntries le2 on le2.ledgerAccounts.id=le1.secondLedgerAccounts.id WHERE le1.voucherType.id = ?1  order by le1.id desc")  order by le1.id desc
	@Query("SELECT le1, le2 FROM LedgerEntries le1 INNER JOIN LedgerEntries le2 on le2.id= le1.id+1  WHERE le1.voucherType.id = ?1 and le1.secondLedgerAccounts.id !=NULL AND le1.createdAt >=?2  AND le1.createdAt <=?3 ")
	Page<LedgerEntries> findByVoucher(Long id, Date startDate, Date endDate,Pageable pageable);
	
	@Query("SELECT le1, le2 FROM LedgerEntries le1 INNER JOIN LedgerEntries le2 on le2.id= le1.id+1  WHERE le1.secondLedgerAccounts.id !=NULL AND le1.voucherType.id != NULL AND le1.createdAt >=?1  AND le1.createdAt <=?2 ")
	Page<LedgerEntries> findByVoucherType(Date startDate, Date endDate, Pageable pageable);

	@Query("SELECT le from LedgerEntries le where le.ledgerAccounts.parentAccount.parentAccount.id=?1")
	Page<LedgerEntries> findBySuperParent(Long id,  Pageable pageable);

	@Query("SELECT le from LedgerEntries le where le.ledgerAccounts.parentAccount.parentAccount.id=?1 and le.createdAt>?2 and le.createdAt<?3")
	Page<LedgerEntries> findBySuperParentByDate(Long id,  Date startDate, Date endDate, Pageable pageable);

	@Query("SELECT le from LedgerEntries le where le.transactionEntryType.id=?1")
	Page<LedgerEntries> findByTransactionType(Long id,  Pageable pageable);
	//LedgerEntries findByAccountName(String accountName);

}

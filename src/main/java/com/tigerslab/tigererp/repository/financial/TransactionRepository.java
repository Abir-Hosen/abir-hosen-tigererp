package com.tigerslab.tigererp.repository.financial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.financial.Transaction;

@Repository("transactionRepository")
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long>{

	@Query("select a from Transaction a where a.date like %?1% or a.id like %?1%")
	Page<Transaction> findAllBySortAndOrder(String date, Pageable pageable);
	
	Transaction findByDate(String date);
	Transaction findTopByOrderByIdDesc();

}

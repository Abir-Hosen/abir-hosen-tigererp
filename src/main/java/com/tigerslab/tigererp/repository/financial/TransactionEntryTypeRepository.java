package com.tigerslab.tigererp.repository.financial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.financial.AccountChart;
import com.tigerslab.tigererp.model.financial.TransactionEntryType;

@Repository("TransactionEntryTypeRepository")
public interface TransactionEntryTypeRepository extends PagingAndSortingRepository<TransactionEntryType, Long>{

	@Query("select a from TransactionEntryType a where a.name like %?1% or a.id like %?1%")
	Page<TransactionEntryType> findAllBySortAndOrder(String name, Pageable pageable);
	
	TransactionEntryType findByName(String name);

}

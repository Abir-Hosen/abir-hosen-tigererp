package com.tigerslab.tigererp.repository.org.inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.inventory.ProductTransactionEntryType;
import com.tigerslab.tigererp.model.org.inventory.Units;

@Repository("productTransactionEntryTypeRepository")
public interface ProductTransactionEntryTypeRepository extends PagingAndSortingRepository<ProductTransactionEntryType, Long>{     

	
	@Query("select u from ProductTransactionEntryType u where u.name like %?1% or u.id like %?1%")
	Page<ProductTransactionEntryType> findAllBySortAndOrder(String name, Pageable pageable);
	
	ProductTransactionEntryType findByName(String name);
}

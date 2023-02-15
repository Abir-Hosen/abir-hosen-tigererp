package com.tigerslab.tigererp.repository.org;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.financial.LedgerGroup;

@Repository("ledgerGroupRepository")
public interface LedgerGroupRepository extends PagingAndSortingRepository<LedgerGroup, Long> {
	
	@Query("select l from LedgerGroup l where l.name like %?1% or l.id like %?1%")
	Page<LedgerGroup> findAllBySortAndOrder(String name, Pageable pageable);
	
	Optional<LedgerGroup> findByName(String name);
}

package com.tigerslab.tigererp.repository.financial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.financial.AccountEquation;

@Repository("accountEquationRepository")
public interface AccountEquationRepository extends PagingAndSortingRepository<AccountEquation, Long>{

	@Query("select a from AccountEquation a where a.name like %?1% or a.id like %?1%")
	Page<AccountEquation> findAllBySortAndOrder(String name, Pageable pageable);
}

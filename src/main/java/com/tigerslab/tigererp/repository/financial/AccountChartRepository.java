package com.tigerslab.tigererp.repository.financial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.financial.AccountChart;

@Repository("accountChart")
public interface AccountChartRepository extends PagingAndSortingRepository<AccountChart, Long>{

	@Query("select a from AccountChart a where a.name like %?1% or a.id like %?1%")
	Page<AccountChart> findAllBySortAndOrder(String name, Pageable pageable);
	
	AccountChart findByName(String name);

}

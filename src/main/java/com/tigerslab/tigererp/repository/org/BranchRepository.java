package com.tigerslab.tigererp.repository.org;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.org.Branch;

@Repository("branchRepository")
public interface BranchRepository extends PagingAndSortingRepository<Branch, Long>{

	@Query("select b from Branch b where b.branchName like %?1% or b.id like %?1%")
	Page<Branch> findAllBySortAndOrder(String branchName, Pageable pageable);
	
	Branch findByBranchName(String branchName);
}

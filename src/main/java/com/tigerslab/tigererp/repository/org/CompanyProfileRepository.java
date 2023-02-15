package com.tigerslab.tigererp.repository.org;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.org.Company;

@Repository("companyProfileRepository")
public interface CompanyProfileRepository extends JpaRepository<Company, Long> {
	
	@Query("select c from Company c where c.companyName like %?1% or c.id like %?1%")
	Page<Company> findAllBySortAndOrder(String companyName, Pageable pageable);
}

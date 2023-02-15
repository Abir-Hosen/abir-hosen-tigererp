package com.tigerslab.tigererp.repository.org;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.user.Country;

@Repository("countryRepository")
public interface CountryRepository extends JpaRepository<Country, Long>{
	
	@Query("select c from Country c where c.niceName like %?1% or c.id like %?1%")
	Page<Country> findAllBySortAndOrder(String niceName, Pageable pageable);
	
}

package com.tigerslab.tigererp.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.user.Gender;

@Repository("genderRepository")
public interface GenderRepository extends PagingAndSortingRepository<Gender, Long>{
	
	@Query("select g from Gender g where g.name like %?1% or g.id like %?1%")
	Page<Gender> findAllBySortAndOrder(String name, Pageable pageable);
	
	Gender findByName(String name);
}

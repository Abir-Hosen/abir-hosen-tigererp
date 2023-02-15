package com.tigerslab.tigererp.repository.org;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.org.PaperType;

@Repository("paperTypeRepository")
public interface PaperTypeRepository extends PagingAndSortingRepository<PaperType, Long>{

	@Query("select b from PaperType b where b.name like %?1% or b.id like %?1%")
	Page<PaperType> findAllBySortAndOrder(String name, Pageable pageable);
	
	PaperType findByName(String name);
}

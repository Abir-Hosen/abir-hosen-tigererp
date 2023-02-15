package com.tigerslab.tigererp.repository.org;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.org.Branch;
import com.tigerslab.tigererp.model.org.StockCategory;

@Repository("stockCategoryRepository")
public interface StockCategoryRepository extends PagingAndSortingRepository<StockCategory, Long>{

	@Query("select s from StockCategory s where s.name like %?1% or s.id like %?1%")
	Page<StockCategory> findAllBySortAndOrder(String name, Pageable pageable);
	
	List<StockCategory> findByParentId(Long parentId);
	
}

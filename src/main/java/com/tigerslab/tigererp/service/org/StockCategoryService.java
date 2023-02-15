package com.tigerslab.tigererp.service.org;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.Branch;
import com.tigerslab.tigererp.model.org.StockCategory;

public interface StockCategoryService {
	
	public Page<StockCategory> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public Optional<StockCategory> findById(Long id);
	
	public StockCategory findByName(String name);
	
	public StockCategory save(StockCategory stockCategory);

	public void deleteById(Long id);
	
	public List<StockCategory> findByParentId(Long parentId);

}

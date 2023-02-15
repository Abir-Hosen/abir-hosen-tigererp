package com.tigerslab.tigererp.service.org.inventory;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.inventory.StockItem;

public interface StockItemService {

	public Page<StockItem> findAllBySortAndOrder(RequestParameter requestParameter);
	public Optional<StockItem> findById(Long id);
	public StockItem save(StockItem stockItem);
	public void deleteById(Long id);
}

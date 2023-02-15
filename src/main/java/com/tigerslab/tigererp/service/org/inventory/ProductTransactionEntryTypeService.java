package com.tigerslab.tigererp.service.org.inventory;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.inventory.ProductTransactionEntryType;

public interface ProductTransactionEntryTypeService {

	public Page<ProductTransactionEntryType> findAllBySortAndOrder(RequestParameter requestParameter);
	public Optional<ProductTransactionEntryType> findById(Long id);
	public ProductTransactionEntryType findByName(String name);
	public ProductTransactionEntryType save(ProductTransactionEntryType productTransactionEntryType);
	public void deleteById(Long id);

}

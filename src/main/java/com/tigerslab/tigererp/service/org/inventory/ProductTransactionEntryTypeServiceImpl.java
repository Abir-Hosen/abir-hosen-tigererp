package com.tigerslab.tigererp.service.org.inventory;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.inventory.ProductTransactionEntryType;
import com.tigerslab.tigererp.repository.org.inventory.ProductTransactionEntryTypeRepository;

@Service("productTransactionEntryTypeService")
public class ProductTransactionEntryTypeServiceImpl implements ProductTransactionEntryTypeService {

	Logger logger = LoggerFactory.getLogger(ProductTransactionEntryTypeService.class);

	@Autowired
	private ProductTransactionEntryTypeRepository  repository;

	private Pageable pagable;
	
	@Override
	public Page<ProductTransactionEntryType> findAllBySortAndOrder(RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findAllBySortAndOrder(requestParameter.getFilter(), pagable);
	}

	@Override
	public Optional<ProductTransactionEntryType> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public ProductTransactionEntryType findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public ProductTransactionEntryType save(ProductTransactionEntryType productTransactionEntryType) {
		return repository.save(productTransactionEntryType);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}

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
import com.tigerslab.tigererp.model.inventory.StockItem;
import com.tigerslab.tigererp.repository.org.inventory.StockItemRepository;

@Service("stockItemService")
public class StockItemServiceImpl implements StockItemService {

	Logger logger = LoggerFactory.getLogger(StockItemService.class);
	
	@Autowired
	private	StockItemRepository repository;

	private Pageable pagable;
	
	@Override
	public Page<StockItem> findAllBySortAndOrder(RequestParameter requestParameter) {
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
		return repository.findAllWithQuantity(pagable);
	}

	@Override
	public Optional<StockItem> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public StockItem save(StockItem stockItem) {
		return repository.save(stockItem);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}

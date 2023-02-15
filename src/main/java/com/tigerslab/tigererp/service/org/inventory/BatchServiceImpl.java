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
import com.tigerslab.tigererp.model.order.Batch;
import com.tigerslab.tigererp.repository.org.inventory.BatchRepository;

@Service("batchService")
public class BatchServiceImpl implements BatchService{
	
	Logger logger = LoggerFactory.getLogger(BatchService.class);
	
	@Autowired
	BatchRepository repository;
	
	Pageable pageable;

	@Override
	public Page<Batch> findAll(RequestParameter requestParameter) {

		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findAll(pageable);
	}

	@Override
	public Optional<Batch> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Batch save(Batch batch) {
		return repository.save(batch);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}

package com.tigerslab.tigererp.service.org.inventory;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.order.Batch;

public interface BatchService {

	public Page<Batch> findAll(RequestParameter requestParameter);
	public Optional<Batch> findById(Long id);
	public Batch save(Batch batch);
	public void deleteById(Long id);

}

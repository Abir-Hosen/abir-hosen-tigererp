package com.tigerslab.tigererp.service.user;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;

public interface CrudInterface {
	
	public Page<?> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public Optional<?> findById(Long id);
	
	public Object save(Object object);

	public void deleteById(Long id);
	
	public void delete(Object object);
	
	public void deleteAll();
}

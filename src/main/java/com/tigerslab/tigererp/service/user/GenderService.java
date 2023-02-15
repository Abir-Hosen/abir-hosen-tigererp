package com.tigerslab.tigererp.service.user;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.user.Gender;

public interface GenderService {
	
	public Page<Gender> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public Optional<Gender> findById(Long id);
	
	public Gender findByName(String name);
	
	public Gender save(Gender gender);

	public void deleteById(Long id);

}

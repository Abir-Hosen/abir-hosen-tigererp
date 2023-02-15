package com.tigerslab.tigererp.service.org;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.PaperType;

public interface PaperTypeService {
	
	public Page<PaperType> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public Optional<PaperType> findById(Long id);
	
	public PaperType findByName(String name);
	
	public PaperType save(PaperType paperType);

	public void deleteById(Long id);

}

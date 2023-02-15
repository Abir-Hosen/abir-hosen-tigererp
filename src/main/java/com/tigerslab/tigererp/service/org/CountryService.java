package com.tigerslab.tigererp.service.org;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.user.Country;

public interface CountryService {
	
	public Page<Country> findAllCountry(RequestParameter requestParam);
	Optional<Country> findById(Long id);

}

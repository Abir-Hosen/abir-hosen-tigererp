package com.tigerslab.tigererp.controller.user.employee;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.user.Country;
import com.tigerslab.tigererp.service.org.CountryService;

@RestController
@RequestMapping("/api/country")
public class CountryController {
	
	@Autowired
	private CountryService countryService;
	
	private Logger logger = LoggerFactory.getLogger(CountryController.class);
	
	@GetMapping
	public ResponseEntity<Page<Country>> countryList(RequestParameter requestParam){
		logger.info("Entering GET method");
		Page<Country> countryList = (Page<Country>) countryService.findAllCountry(requestParam);
		return ResponseEntity.ok(countryList);
	}
}

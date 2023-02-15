package com.tigerslab.tigererp.service.org;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigerslab.tigererp.model.user.PhoneFormatFullCountry;
import com.tigerslab.tigererp.repository.org.PhoneFormatFullCountryRepository;

@Service("phoneFormatFullCountryService")
public class PhoneFormatFullCountryServiceIml implements PhoneFormatFullCountryService {

	@Autowired
	private PhoneFormatFullCountryRepository phoneFormatFullCountryRepository;
	
	@Override
	public Optional<PhoneFormatFullCountry> findById(Long id) {
		
		return phoneFormatFullCountryRepository.findById(id);
	}

	@Override
	public PhoneFormatFullCountry save(PhoneFormatFullCountry phoneFormatFullCountry) {
		// TODO Auto-generated method stub
		return phoneFormatFullCountryRepository.save(phoneFormatFullCountry);
	}

}

package com.tigerslab.tigererp.service.org;

import java.util.Optional;

import com.tigerslab.tigererp.model.user.PhoneFormatFullCountry;

public interface PhoneFormatFullCountryService {

	Optional<PhoneFormatFullCountry> findById(Long id);
	PhoneFormatFullCountry save(PhoneFormatFullCountry phoneFormatFullCountry);
}

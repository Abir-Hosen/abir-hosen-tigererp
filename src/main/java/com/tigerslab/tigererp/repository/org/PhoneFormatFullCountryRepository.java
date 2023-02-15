package com.tigerslab.tigererp.repository.org;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.user.PhoneFormatFullCountry;

@Repository("phoneFormatFullCountryRepository")
public interface PhoneFormatFullCountryRepository extends PagingAndSortingRepository<PhoneFormatFullCountry, Long>{

}

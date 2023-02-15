package com.tigerslab.tigererp.repository.user.employee;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.user.AddressV2;

@Repository("addressV2Repository")
public interface AddressV2Repository extends PagingAndSortingRepository<AddressV2, Long>{

}

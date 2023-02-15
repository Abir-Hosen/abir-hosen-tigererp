package com.tigerslab.tigererp.service.user.employee;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tigerslab.tigererp.model.user.AddressV2;
import com.tigerslab.tigererp.repository.user.employee.AccessPermissionRepository;
import com.tigerslab.tigererp.repository.user.employee.AddressV2Repository;

@Service("addressV2Service")
public class AddressV2ServiceImpl implements AddressV2Service {

	@Autowired
	private AddressV2Repository addressV2Repository;
	
	private Pageable pagable;
	
	@Override
	public Optional<AddressV2> findById(Long id) {
		return addressV2Repository.findById(id);
	}

}

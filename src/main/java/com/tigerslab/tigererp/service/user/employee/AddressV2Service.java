package com.tigerslab.tigererp.service.user.employee;

import java.util.Optional;

import com.tigerslab.tigererp.model.user.AddressV2;

public interface AddressV2Service {

	public Optional<AddressV2> findById(Long id);
}

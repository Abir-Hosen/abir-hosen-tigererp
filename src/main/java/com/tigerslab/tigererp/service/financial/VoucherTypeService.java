package com.tigerslab.tigererp.service.financial;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.financial.VoucherType;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;

public interface VoucherTypeService {

	
	public Page<VoucherType> findAllBySortAndOrder(RequestParameter requestParameter);
	public Optional<VoucherType> findById(Long id);
	public VoucherType save(VoucherType voucherType);

}

package com.tigerslab.tigererp.service.financial;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tigerslab.tigererp.model.financial.VoucherType;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.repository.financial.VoucherTypeRepository;

@Service("voucherTypeService")
public class VoucherTypeServiceImpl implements VoucherTypeService {

	Logger logger = LoggerFactory.getLogger(VoucherTypeService.class);
	
	@Autowired
	private VoucherTypeRepository repository;

	private Pageable pagable;

	@Override
	public Optional<VoucherType> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public VoucherType save(VoucherType voucherType) {
		return repository.save(voucherType);
	}

	@Override
	public Page<VoucherType> findAllBySortAndOrder(RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findAllBySortAndOrder(requestParameter.getFilter(), pagable);
	}

}

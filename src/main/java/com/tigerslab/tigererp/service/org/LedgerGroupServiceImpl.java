package com.tigerslab.tigererp.service.org;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tigerslab.tigererp.model.financial.LedgerGroup;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.repository.org.LedgerGroupRepository;

@Service("ledgerGroupService")
public class LedgerGroupServiceImpl implements LedgerGroupService {

	Logger logger = LoggerFactory.getLogger(LedgerGroupService.class);
	@Autowired
	private LedgerGroupRepository ledgerGroupRepository;
	
	private Pageable pagable;
	
	@Override
	public Page<LedgerGroup> findAllBySortAndOrder(RequestParameter requestParam) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParam.getPage() >= 1) {
			requestParam.setPage(requestParam.getPage()-1);
		}
		if(requestParam.getOrder().charAt(0) == '-') {
			String sortOrder = requestParam.getOrder().substring(1);
			pagable = PageRequest.of(requestParam.getPage(), requestParam.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParam.getPage(), requestParam.getLimit(), Sort.by(requestParam.getOrder()).ascending());
		}
		return ledgerGroupRepository.findAllBySortAndOrder(requestParam.getFilter(), pagable);
	}

	@Override
	public Optional<LedgerGroup> findById(Long id) {
		return ledgerGroupRepository.findById(id);
	}

	@Override
	public Optional<LedgerGroup> findByName(String name) {
		return ledgerGroupRepository.findByName(name);
	}

	@Override
	public LedgerGroup save(LedgerGroup ledgerGroup) {
		return ledgerGroupRepository.save(ledgerGroup);
	}
	

	@Override
	public void deleteById(Long id) {
		ledgerGroupRepository.deleteById(id);
	}

}

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

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.Branch;
import com.tigerslab.tigererp.repository.org.BranchRepository;

@Service("branchService")
public class BranchServiceImpl implements BranchService {

	Logger logger = LoggerFactory.getLogger(BranchService.class);

	@Autowired
	BranchRepository branchRepository;

	private Pageable pagable;
	
	@Override
	public Page<Branch> findAllBySortAndOrder(RequestParameter requestParam) {
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
		return branchRepository.findAllBySortAndOrder(requestParam.getFilter(), pagable);
	}

	@Override
	public Optional<Branch> findById(Long id) {
		return branchRepository.findById(id);
	}

	@Override
	public Branch findByName(String branchName) {
		return branchRepository.findByBranchName(branchName);
	}

	@Override
	public Branch save(Branch branch) {
		return branchRepository.save(branch);
	}

	@Override
	public void deleteById(Long id) {
		branchRepository.deleteById(id);
	}

}

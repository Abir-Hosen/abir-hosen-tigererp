package com.tigerslab.tigererp.service.org;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.Branch;

public interface BranchService {
	
	public Page<Branch> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public Optional<Branch> findById(Long id);
	
	public Branch findByName(String branchName);
	
	public Branch save(Branch branch);

	public void deleteById(Long id);

}

package com.tigerslab.tigererp.service.financial;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.financial.AccountChart;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;

public interface AccountChartService {
	
	public Page<AccountChart> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public Optional<AccountChart> findById(Long id);
	
	public AccountChart findByName(String name);
	
	public AccountChart save(AccountChart accountChart);

	public void deleteById(Long id);

}

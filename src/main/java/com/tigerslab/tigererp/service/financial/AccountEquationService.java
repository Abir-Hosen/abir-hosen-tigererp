package com.tigerslab.tigererp.service.financial;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.financial.AccountEquation;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.user.employee.Employee;

public interface AccountEquationService {

	public Page<AccountEquation> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public AccountEquation save(AccountEquation accountEquation);
}

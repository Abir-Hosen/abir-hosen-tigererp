package com.tigerslab.tigererp.repository.user.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.user.employee.EmployeeCategory;

@Repository("employeeCategoryRepository")
public interface EmployeeCategoryRepository  extends PagingAndSortingRepository<EmployeeCategory, Long> {

	@Query("select t from EmployeeCategory t where t.categoryName like %?1% or t.id like %?1%")
	Page<EmployeeCategory> findAllBySortAndOrder(String categoryName, Pageable pageable);
}

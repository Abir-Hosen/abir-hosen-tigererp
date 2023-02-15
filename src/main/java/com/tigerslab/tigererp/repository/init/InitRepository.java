package com.tigerslab.tigererp.repository.init;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.init.ApplicationInfo;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;

@Repository("initRepository")
public interface InitRepository extends PagingAndSortingRepository<ApplicationInfo, Long> {
	
	@Query("select t from ApplicationInfo t")
	Page<ApplicationInfo> findAllBySortAndOrder(Pageable pageable);

}

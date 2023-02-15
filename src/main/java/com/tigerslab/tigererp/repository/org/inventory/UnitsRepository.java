package com.tigerslab.tigererp.repository.org.inventory;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.org.inventory.Units;
import com.tigerslab.tigererp.model.user.employee.Employee;

@Repository("unitsRepository")
public interface UnitsRepository extends JpaRepository<Units, Long> {
	
	@Query("select u from Units u where u.formalName like %?1% or u.id like %?1%")
	Page<Units> findAllBySortAndOrder(String formalName, Pageable pageable);
}

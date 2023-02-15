package com.tigerslab.tigererp.repository.financial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.financial.VoucherType;


@Repository("voucherTypeRepository")
public interface VoucherTypeRepository extends PagingAndSortingRepository<VoucherType, Long> {

	@Query("select b from VoucherType b where b.name like %?1% or b.id like %?1%")
	Page<VoucherType> findAllBySortAndOrder(String name, Pageable pageable);

}

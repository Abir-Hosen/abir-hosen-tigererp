package com.tigerslab.tigererp.repository.org.inventory;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.order.Batch;

@Repository("batchRepository")
public interface BatchRepository extends PagingAndSortingRepository<Batch, Long>{

}

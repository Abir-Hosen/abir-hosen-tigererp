package com.tigerslab.tigererp.repository.org.inventory;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.inventory.StockItem;

@Repository("stockItemRepository")
public interface StockItemRepository extends PagingAndSortingRepository<StockItem, Long>{
	
	@Query("select s, sum(o.quantity) from StockItem s left join OrderStockItem o ON o.stockItem=s.id group by s")
	Page<StockItem> findAllWithQuantity(Pageable pageable);

	@Query("select s.name, SUM(o.quantity) from StockItem s INNER JOIN OrderStockItem o ON o.stockItem.id=s.id AND o.createdAt>=?1 AND o.createdAt<=?2 AND o.productTransactionEntryType.id IN (2) GROUP BY s ORDER BY s.id ASC")
	Page<StockItem> findAllByItemWithOrderQuantityBySale(Date startDate, Date endDate, Pageable pageable);
}

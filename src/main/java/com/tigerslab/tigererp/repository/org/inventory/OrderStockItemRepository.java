package com.tigerslab.tigererp.repository.org.inventory;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.inventory.OrderStockItem;
import com.tigerslab.tigererp.model.inventory.StockItem;

@Repository("orderStockItemRepository")
public interface OrderStockItemRepository extends PagingAndSortingRepository<OrderStockItem, Long>{

	@Query("select o.id, o.quantity, sum(o.quantity) from OrderStockItem o where o.stockItem=?1")
	Page<OrderStockItem> findTotalQuantityByStockItemId(Long id, Pageable pageable);

	@Query("select s, o, SUM(o2.quantity) from StockItem s INNER JOIN OrderStockItem o On o.stockItem.id=s.id AND o.productTransactionEntryType.id IN (1,2,3,4,5,6,7,8) INNER JOIN OrderStockItem o2 ON o2.id<=o.id AND o2.stockItem.id=s.id AND o2.productTransactionEntryType.id IN (1,2,3,4,5,6,7,8) WHERE o.createdAt>=?2 AND o.createdAt<=?3 AND s.id=?1 GROUP BY o, s ORDER BY o.createdAt ASC")
	Page<OrderStockItem> findByItemWithOrderQuantity(Long id, Date startDate, Date endDate, Pageable pageable);

	@Query("select s, o, SUM(o2.quantity) from StockItem s INNER JOIN OrderStockItem o On o.stockItem.id=s.id AND o.productTransactionEntryType.id IN (1,2,3,4,5,6,7,8) INNER JOIN OrderStockItem o2 ON o2.id<=o.id AND o2.stockItem.id=s.id AND o2.productTransactionEntryType.id IN (1,2,3,4,5,6,7,8) WHERE o.createdAt>=?1 AND o.createdAt<=?2 GROUP BY o, s ORDER BY o.createdAt ASC")
	Page<OrderStockItem> findAllByItemWithOrderQuantity(Date startDate, Date endDate, Pageable pageable);

	@Query("select o.rate, o.createdAt, o.quantity, o.discount from OrderStockItem o WHERE o.productTransactionEntryType.id=?1 AND o.createdAt>=?2 AND o.createdAt<=?3 ORDER BY o.id DESC")
	Page<OrderStockItem> findOrderQuantityByTypeByDate(Long id, Date startDate, Date endDate, Pageable pageable);

//	@Query("select SUM(o2.quantity) from OrderStockItem o INNER JOIN OrderStockItem o2 on o2.productTransactionEntryType.id=?1  AND o2.createdAt>=?2 AND o2.createdAt<=?3")
//	Long findTotalQuantityByPTTId(Long id, Date startDate, Date endDate, Pageable pageable);


}

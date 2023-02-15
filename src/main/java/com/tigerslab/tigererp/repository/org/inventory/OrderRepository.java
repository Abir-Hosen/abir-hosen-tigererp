package com.tigerslab.tigererp.repository.org.inventory;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.custom.OrderTotalByDateRange;
import com.tigerslab.tigererp.model.inventory.Order;

@Repository("orderRepository")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long>{

	@Query("select u from Order u where u.id like %?1%")
	Page<Order> findAllBySortAndOrder(String string, Pageable pageable);

	@Query("select u from Order u JOIN u.stockItems osi WHERE osi.productTransactionEntryType.id = ?1 AND u.orderDate >=?2  AND u.orderDate <=?3 AND u.party.accountName like %?4%  GROUP BY u.id")
	Page<Order> findByOrderStockItemProductTransactionEntryTypeSortAndOrder(Long id, Date startDate, Date endDate, String string, Pageable pageable);

	@Query("select u from Order u JOIN u.stockItems osi JOIN osi.stockItem si WHERE osi.productTransactionEntryType.id = ?1  AND si.id=?2 ORDER BY u.id DESC")
	Page<Order> findByOrderStockItemSpecificProduct(Long id, Long itemId, Pageable pageable);

	@Query("select new com.tigerslab.tigererp.model.custom.OrderTotalByDateRange(u, SUM(osi.quantity*-1), AVG(osi.rate), SUM(osi.quantity*-1*osi.rate-(((osi.quantity*-1*osi.rate)*osi.discount))/100)) from Order u JOIN u.stockItems osi JOIN osi.stockItem si WHERE osi.productTransactionEntryType.id = ?1 AND u.orderDate >=?2  AND u.orderDate <=?3 AND u.party.accountName like %?4%  GROUP BY si.id")
	Page<OrderTotalByDateRange> findByOrderStockItemForProfitLoss(Long id, Date startDate, Date endDate, String string, Pageable pageable);
	
	@Query("select new com.tigerslab.tigererp.model.custom.OrderTotalByDateRange(u, SUM(osi.quantity), AVG(osi.rate), SUM(osi.quantity*osi.rate-(((osi.quantity*osi.rate)*osi.discount))/100)) from Order u JOIN u.stockItems osi JOIN osi.stockItem si WHERE osi.productTransactionEntryType.id = ?1 AND u.orderDate >=?2  AND u.orderDate <=?3 AND si.id=?4  GROUP BY si.id")
	Page<OrderTotalByDateRange> findByOrderStockItemIndivItemForProfitLoss(Long id, Date startDate, Date endDate, Long itemId, Pageable pageable);
	
	@Query("select o.id, SUM(o2.paidAmount) from Order o INNER JOIN Order o2 ON o2.orderAccounts.id=?1 WHERE o.orderAccounts.id = ?1 AND o.orderDate >=?2  AND o.orderDate <=?3 GROUP BY o.id ORDER BY o.id DESC")
	Page<Order> findPaidAmountByPartyId(Long id, Date startDate, Date endDate, Pageable pageable);
}

package com.tigerslab.tigererp.service.org.inventory;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.inventory.Order;
import com.tigerslab.tigererp.model.inventory.OrderStockItem;
import com.tigerslab.tigererp.model.inventory.StockItem;

public interface OrderService {

	public Page<Order> findAllBySortAndOrder(RequestParameter requestParameter);
	public Page<OrderStockItem> findStockItemByQuantity(Long id, Date startDate, Date endDate, RequestParameter requestParameter);
	public Page<Order> findPaidAmountByPartyId(Long id, Date startDate, Date endDate, RequestParameter requestParameter);
	public Page<OrderStockItem> findAllStockItemByQuantity(Date startDate, Date endDate, RequestParameter requestParameter);
	public Page<OrderStockItem> findQuantityByTypeByDate(Long id, Date startDate, Date endDate, RequestParameter requestParameter);
	public Page<StockItem> findAllStockItemByTotalSell(Date startDate, Date endDate, RequestParameter requestParameter);
	public Page<Order> findByOrderStockItemProductTransactionEntryTypeSortAndOrder(Long id, Date startDate, Date endDate, RequestParameter requestParameter);
	public Page<Order> findByOrderStockItemSpecificProduct(Long id,Long itemId, RequestParameter requestParameter);
	public Page<?> findByOrderStockItemForProfitLoss(Long id, Date startDate, Date endDate, RequestParameter requestParameter);
	public Page<?> findByOrderStockItemIndivItemForProfitLoss(Long id, Date startDate, Date endDate, Long itemId, RequestParameter requestParameter);
	public Optional<Order> findById(Long id);
	public Order save(Order order);
	public void deleteById(Long id);
	
}

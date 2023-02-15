package com.tigerslab.tigererp.service.org.inventory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tigerslab.tigererp.model.financial.LedgerEntries;
import com.tigerslab.tigererp.model.financial.Transaction;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.inventory.Order;
import com.tigerslab.tigererp.model.inventory.OrderStockItem;
import com.tigerslab.tigererp.model.inventory.StockItem;
import com.tigerslab.tigererp.repository.org.inventory.OrderRepository;
import com.tigerslab.tigererp.repository.org.inventory.OrderStockItemRepository;
import com.tigerslab.tigererp.repository.org.inventory.StockItemRepository;
import com.tigerslab.tigererp.service.financial.LedgerAccountsService;
import com.tigerslab.tigererp.service.financial.LedgerEntriesService;
import com.tigerslab.tigererp.service.financial.TransactionService;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

	Logger logger = LoggerFactory.getLogger(OrderService.class);
	Pageable pageable;
	
	@Autowired
	OrderRepository repository;
	
	@Autowired
	StockItemRepository stockItemRepository;
	
	@Autowired
	OrderStockItemRepository orderStockItemRepository;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	LedgerEntriesService ledgerEntriesService;
	
	@Autowired
	LedgerAccountsService ledgerAccountsService;
	
	@Override
	public Page<Order> findAllBySortAndOrder(RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findAllBySortAndOrder(requestParameter.getFilter(), pageable);
	}
	
	@Override
	public Page<Order> findByOrderStockItemProductTransactionEntryTypeSortAndOrder(Long id,   Date startDate, Date endDate, RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findByOrderStockItemProductTransactionEntryTypeSortAndOrder(id, startDate, endDate, requestParameter.getFilter(), pageable);
	}
	
	@Override
	public Page<?> findByOrderStockItemForProfitLoss(Long id,   Date startDate, Date endDate, RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findByOrderStockItemForProfitLoss(id, startDate, endDate, requestParameter.getFilter(), pageable);
	}
	
	@Override
	public Page<?> findByOrderStockItemIndivItemForProfitLoss(Long id,   Date startDate, Date endDate,Long itemId, RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findByOrderStockItemIndivItemForProfitLoss(id, startDate, endDate, itemId, pageable);
	}
	
	@Override
	public Page<Order> findByOrderStockItemSpecificProduct(Long id, Long itemId, RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findByOrderStockItemSpecificProduct(id, itemId, pageable);
	}

	@Override
	public Page<Order> findPaidAmountByPartyId(Long id,   Date startDate, Date endDate, RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findPaidAmountByPartyId(id, startDate, endDate, pageable);
	}

	@Override
	public Page<OrderStockItem> findStockItemByQuantity(Long id,   Date startDate, Date endDate, RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return orderStockItemRepository.findByItemWithOrderQuantity(id, startDate, endDate, pageable);
	}
	
	@Override
	public Page<OrderStockItem> findAllStockItemByQuantity(Date startDate, Date endDate, RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return orderStockItemRepository.findAllByItemWithOrderQuantity(startDate, endDate, pageable);
	}

	@Override
	public Page<OrderStockItem> findQuantityByTypeByDate(Long id, Date startDate, Date endDate, RequestParameter requestParameter) {
		
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return orderStockItemRepository.findOrderQuantityByTypeByDate(id, startDate, endDate, pageable);
	}
	
	@Override
	public Page<StockItem> findAllStockItemByTotalSell(Date startDate, Date endDate, RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pageable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return stockItemRepository.findAllByItemWithOrderQuantityBySale(startDate, endDate, pageable);
	}

	@Override
	public Optional<Order> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Order save(Order order) {
		Order savedOrder = new Order();
		if(order.getId() == null) {
			
			savedOrder = repository.save(order);
			
			System.out.println(order.getStockItems()+"\n\n\nStock item\n\n\n");
			
			Long typeId = (long)0;
			for (int i=0; i<order.getStockItems().size(); i++) {
				System.out.println("\n type id");
				typeId = order.getStockItems().get(i).getProductTransactionEntryType().getId();
				System.out.println("\n"+typeId);
			}
			
			LedgerEntries le[] = new LedgerEntries[2];
			
			LedgerEntries le1 = new LedgerEntries();
			LedgerEntries le2 = new LedgerEntries();
			
			if((order.getOrderAccounts().getId()==1) || (order.getOrderAccounts().getId()==5) || (order.getOrderAccounts().getId()==5 && order.getParty().getId()==3) || (order.getOrderAccounts().getId()==3 && order.getParty().getId()==5)) {
				if((long) order.getStockItems().iterator().next().getProductTransactionEntryType().getId() == 5 ||
						(long) order.getStockItems().iterator().next().getProductTransactionEntryType().getId() == 8) {
					le1.setLedgerAccounts(ledgerAccountsService.findById(order.getParty().getId()).get());
					le1.setSecondLedgerAccounts(ledgerAccountsService.findById(order.getOrderAccounts().getId()).get());
					le2.setLedgerAccounts(ledgerAccountsService.findById(order.getOrderAccounts().getId()).get());
				}else {
					le1.setLedgerAccounts(ledgerAccountsService.findById(order.getOrderAccounts().getId()).get());
					le1.setSecondLedgerAccounts(ledgerAccountsService.findById(order.getParty().getId()).get());
					le2.setLedgerAccounts(ledgerAccountsService.findById(order.getParty().getId()).get());
				}
			}
			else if((order.getOrderAccounts().getId()==3) ) {
				if((order.getStockItems().iterator().next().getProductTransactionEntryType().getId() == 6) ) {
					le1.setLedgerAccounts(ledgerAccountsService.findById(order.getOrderAccounts().getId()).get());
					le1.setSecondLedgerAccounts(ledgerAccountsService.findById(order.getParty().getId()).get());
					le2.setLedgerAccounts(ledgerAccountsService.findById(order.getParty().getId()).get());
				}else {
					le1.setLedgerAccounts(ledgerAccountsService.findById(order.getParty().getId()).get());
					le1.setSecondLedgerAccounts(ledgerAccountsService.findById(order.getOrderAccounts().getId()).get());
					le2.setLedgerAccounts(ledgerAccountsService.findById(order.getOrderAccounts().getId()).get());
				}
			}
			
//			le1.setLedgerAccounts(ledgerAccountsService.findById(order.getOrderAccounts().getId()).get());// To remove after uncomment those above lines
//			le1.setSecondLedgerAccounts(ledgerAccountsService.findById(order.getParty().getId()).get());// To remove after uncomment those above lines
//			
			Transaction t1 = new Transaction();
			t1.setAmount(order.getTotalAmount());
			if(t1.getDescription() == null) {
				t1.setDescription("");
			}
			if(order.getDescription() == null) {
				order.setDescription("");
			}
			t1.setDescription(order.getDescription().toString()+" Ref Order Id# ".concat(order.getId().toString()));
			t1.setOriginalDate(new Date());
			le1.setTransactions(t1);

//			le2.setLedgerAccounts(ledgerAccountsService.findById(order.getParty().getId()).get());// To remove after uncomment those above lines
			
			Transaction t2 = new Transaction();
			t2.setAmount(order.getTotalAmount());
			if(t2.getDescription() == null) {
				t2.setDescription("");
			}
			t2.setDescription(order.getDescription().toString()+" Ref Order Id# ".concat(order.getId().toString()));
			t2.setOriginalDate(new Date());
			le2.setTransactions(t2);
			
			le1.setOrder(savedOrder);
			le2.setOrder(savedOrder);
			le[0] = le1;
			le[1] = le2;
			
			transactionService.makeDoubleEntryTransaction(le);
			
			if(!order.getPaidAmount().equals(BigDecimal.ZERO)) {
				System.out.println("Here Paid amount is zero;");
				
				LedgerEntries leP[] = new LedgerEntries[2];
				
				LedgerEntries leP1 = new LedgerEntries();
				LedgerEntries leP2 = new LedgerEntries();
				
				if(order.getOrderAccounts().getId()==1) {
					if(order.getStockItems().iterator().next().getProductTransactionEntryType().getId() == 5) {
						leP1.setLedgerAccounts(ledgerAccountsService.findById((long) 2).get());
						leP1.setSecondLedgerAccounts(ledgerAccountsService.findById(order.getParty().getId()).get());
						leP2.setLedgerAccounts(ledgerAccountsService.findById(order.getParty().getId()).get());
					}else {
						leP1.setLedgerAccounts(ledgerAccountsService.findById(order.getParty().getId()).get());
						leP1.setSecondLedgerAccounts(ledgerAccountsService.findById((long) 2).get());
						leP2.setLedgerAccounts(ledgerAccountsService.findById((long) 2).get());
					}
				} else if(order.getOrderAccounts().getId()==3) {
					if(order.getStockItems().iterator().next().getProductTransactionEntryType().getId() == 6) {
						leP1.setLedgerAccounts(ledgerAccountsService.findById(order.getParty().getId()).get());
						leP1.setSecondLedgerAccounts(ledgerAccountsService.findById((long) 2).get());
						leP2.setLedgerAccounts(ledgerAccountsService.findById((long) 2).get());
					}else {
						leP1.setLedgerAccounts(ledgerAccountsService.findById((long) 2).get());
						leP1.setSecondLedgerAccounts(ledgerAccountsService.findById(order.getParty().getId()).get());
						leP2.setLedgerAccounts(ledgerAccountsService.findById(order.getParty().getId()).get());
					}
				}
				
//				leP1.setLedgerAccounts(ledgerAccountsService.findById(order.getParty().getId()).get());// To remove after uncomment those above lines
//				leP1.setSecondLedgerAccounts(ledgerAccountsService.findById((long) 2).get());// To remove after uncomment those above lines
//				
				Transaction tP1 = new Transaction();
				tP1.setAmount(order.getPaidAmount());
				tP1.setDescription(order.getDescription().toString()+" Ref Order Id# ".concat(order.getId().toString()));
				tP1.setOriginalDate(new Date());
				leP1.setTransactions(tP1);
				
//				leP2.setLedgerAccounts(ledgerAccountsService.findById((long) 2).get());// To remove after uncomment those above lines
				
				Transaction tP2 = new Transaction();
				tP2.setAmount(order.getPaidAmount());
				tP2.setDescription(order.getDescription().toString()+" Ref Order Id# ".concat(order.getId().toString()));
				tP2.setOriginalDate(new Date());
				leP2.setTransactions(tP2);

				leP1.setOrder(savedOrder);
				leP2.setOrder(savedOrder);
				
				leP[0] = leP1;
				leP[1] = leP2;
				
				transactionService.makeDoubleEntryTransaction(leP);
				
			}else {
				System.out.println("Here Paid amount is not zero;");
			}
			for(int i = 0 ; i< order.getStockItems().size(); i++) {
				if(order.getStockItems().get(i).getProductTransactionEntryType().getId()==1 || order.getStockItems().get(i).getProductTransactionEntryType().getId()==6 || order.getStockItems().get(i).getProductTransactionEntryType().getId()==7 || order.getStockItems().get(i).getProductTransactionEntryType().getId()==3) {
					System.out.println("Anything to change for type of purchase!");
				}
				else if(order.getStockItems().get(i).getProductTransactionEntryType().getId()==2 || order.getStockItems().get(i).getProductTransactionEntryType().getId()==5 || order.getStockItems().get(i).getProductTransactionEntryType().getId()==8 || order.getStockItems().get(i).getProductTransactionEntryType().getId()==4) {
					System.out.println("Anything to change for type of sale!");
					order.getStockItems().get(i).setQuantity(order.getStockItems().get(i).getQuantity()*(-1));
				}
				else {
					System.out.println("Warning: No product transaction type selected!");
				}
			}
		}
		else {
			System.out.println("Order Id check Problem..");
		}
		return savedOrder;
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}

package com.tigerslab.tigererp.service.financial;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tigerslab.tigererp.model.financial.LedgerEntries;
import com.tigerslab.tigererp.model.financial.VoucherType;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.repository.financial.LedgerEntriesRepository;

@Service("ledgerEntriesService")
public class LedgerEntriesServiceImpl implements LedgerEntriesService {

	Logger logger = LoggerFactory.getLogger(LedgerEntriesServiceImpl.class);

	@Autowired
	private LedgerEntriesRepository repository;

	private Pageable pagable;


	@Override
	public Page<LedgerEntries> findBySuperParent(Long id, RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findBySuperParent(id, pagable);
	}

	@Override
	public Page<LedgerEntries> findBySuperParentByDate(Long id, Date startDate, Date endDate, RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		//return null;
		return repository.findBySuperParentByDate(id, startDate, endDate, pagable);
	}
	@Override
	public Page<LedgerEntries> findByTransactionType(Long id, RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findByTransactionType(id, pagable);
	}

	@Override
	public Page<LedgerEntries> findAllBySortAndOrder(RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findAllBySortAndOrder(requestParameter.getFilter(), pagable);
	}
	
	@Override
	public Page<LedgerEntries> findByAccountIdAndCustomQuery(Long id, RequestParameter requestParameter) {
		logger.info("Entering findByCusto Query Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findByLedgerAccountsId(id, pagable);
	}

	@Override
	public Page<LedgerEntries> findByVoucher(Long id,  Date startDate, Date endDate,  RequestParameter requestParameter) {
		logger.info("Entering findByCusto Query Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findByVoucher(id, startDate, endDate, pagable);
	}

	@Override
	public Page<LedgerEntries> findByVoucherType( Date startDate, Date endDate,  RequestParameter requestParameter) {
		logger.info("Entering findByCusto Query Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findByVoucherType(startDate, endDate, pagable);
	}
	
	@Override
	public Optional<LedgerEntries> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public LedgerEntries findByName(String accountName) {
		return null;
	}

	@Override
	public LedgerEntries save(LedgerEntries ledgerEntries) {
		return repository.save(ledgerEntries);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}

package com.tigerslab.tigererp.service.org;

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

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.Branch;
import com.tigerslab.tigererp.model.org.StockCategory;
import com.tigerslab.tigererp.repository.org.BranchRepository;
import com.tigerslab.tigererp.repository.org.StockCategoryRepository;

@Service("stockCategoryService")
public class StockCategoryServiceImpl implements StockCategoryService {

	Logger logger = LoggerFactory.getLogger(StockCategoryServiceImpl.class);

	@Autowired
	StockCategoryRepository stockCategoryRepository;

	private Pageable pagable;
	
	@Override
	public Page<StockCategory> findAllBySortAndOrder(RequestParameter requestParam) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParam.getPage() >= 1) {
			requestParam.setPage(requestParam.getPage()-1);
		}
		if(requestParam.getOrder().charAt(0) == '-') {
			String sortOrder = requestParam.getOrder().substring(1);
			pagable = PageRequest.of(requestParam.getPage(), requestParam.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParam.getPage(), requestParam.getLimit(), Sort.by(requestParam.getOrder()).ascending());
		}
		return stockCategoryRepository.findAllBySortAndOrder(requestParam.getFilter(), pagable);
	}

	@Override
	public Optional<StockCategory> findById(Long id) {
		return stockCategoryRepository.findById(id);
	}

	@Override
	public StockCategory findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockCategory save(StockCategory stockCategory) {
		return stockCategoryRepository.save(stockCategory);
	}

	@Override
	public void deleteById(Long id) {
		stockCategoryRepository.deleteById(id);
	}

	@Override
	public List<StockCategory> findByParentId(Long parentId) {
		// TODO Auto-generated method stub
		return stockCategoryRepository.findByParentId(parentId);
	}

}

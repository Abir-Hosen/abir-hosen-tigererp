package com.tigerslab.tigererp.service.org;

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
import com.tigerslab.tigererp.model.org.PaperType;
import com.tigerslab.tigererp.repository.org.PaperTypeRepository;

@Service("paperTypeService")
public class PaperTypeServiceImpl implements PaperTypeService {

	Logger logger = LoggerFactory.getLogger(PaperTypeService.class);

	@Autowired
	PaperTypeRepository paperTypeRepository;

	private Pageable pagable;
	
	@Override
	public Page<PaperType> findAllBySortAndOrder(RequestParameter requestParam) {
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
		return paperTypeRepository.findAllBySortAndOrder(requestParam.getFilter(), pagable);
	}

	@Override
	public Optional<PaperType> findById(Long id) {
		return paperTypeRepository.findById(id);
	}

	@Override
	public PaperType findByName(String paperTypeName) {
		return paperTypeRepository.findByName(paperTypeName);
	}

	@Override
	public PaperType save(PaperType paperType) {
		return paperTypeRepository.save(paperType);
	}

	@Override
	public void deleteById(Long id) {
		paperTypeRepository.deleteById(id);
	}

}

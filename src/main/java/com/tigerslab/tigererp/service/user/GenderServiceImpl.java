package com.tigerslab.tigererp.service.user;

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
import com.tigerslab.tigererp.model.user.Gender;
import com.tigerslab.tigererp.repository.user.GenderRepository;

@Service("genderService")
public class GenderServiceImpl implements GenderService {

	Logger logger = LoggerFactory.getLogger(GenderService.class);
	@Autowired
	private GenderRepository genderRepository;
	private Pageable pagable;
	@Override
	public Page<Gender> findAllBySortAndOrder(RequestParameter requestParam) {
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
		return genderRepository.findAllBySortAndOrder(requestParam.getFilter(), pagable);
	}

	@Override
	public Optional<Gender> findById(Long id) {
		return genderRepository.findById(id);
	}

	@Override
	public Gender findByName(String name) {
		return genderRepository.findByName(name);
	}

	@Override
	public Gender save(Gender gender) {
		return genderRepository.save(gender);
	}

	@Override
	public void deleteById(Long id) {
		genderRepository.deleteById(id);
	}

}

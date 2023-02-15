package com.tigerslab.tigererp.service.init;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.init.ApplicationInfo;
import com.tigerslab.tigererp.repository.init.InitRepository;

@Service("initService")
public class InitServiceImpl implements InitService {
	
	@Autowired
	private InitRepository initRepository;

	@Override
	public Page<?> findAllBySortAndOrder(RequestParameter requestParameter) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(0, 1000);
		System.out.println("FROM SERVICE: "+initRepository.findAllBySortAndOrder(pageable));
		return (Page<?>) initRepository.findAll();
	}

	@Override
	public Optional<?> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object save(Object object) {
		// TODO Auto-generated method stub
		return initRepository.save((ApplicationInfo)object);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

}

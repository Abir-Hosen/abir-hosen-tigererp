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
import com.tigerslab.tigererp.model.org.Room;
import com.tigerslab.tigererp.repository.org.RoomRepository;

@Service("roomService")
public class RoomServiceImpl implements RoomService {

	Logger logger = LoggerFactory.getLogger(RoomService.class);

	@Autowired
	RoomRepository roomRepository;
	
	private Pageable pagable;
	
	@Override
	public Page<Room> findAllBySortAndOrder(RequestParameter requestParam) {
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
		return roomRepository.findAllBySortAndOrder(requestParam.getFilter(), pagable);
	}

	@Override
	public Optional<Room> findById(Long id) {
		return roomRepository.findById(id);
	}

	@Override
	public Page<Room> findByBranchId(Long id, RequestParameter requestParam) {
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
		return roomRepository.findByBranchId(id, pagable);
	}

	@Override
	public Room findByName(String roomName) {
		return roomRepository.findByRoomName(roomName);
	}

	@Override
	public Room save(Room room) {
		return roomRepository.save(room);
	}

	@Override
	public void deleteById(Long id) {
		roomRepository.deleteById(id);
	}

}

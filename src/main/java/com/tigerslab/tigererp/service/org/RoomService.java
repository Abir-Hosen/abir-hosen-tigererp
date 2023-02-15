package com.tigerslab.tigererp.service.org;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.Room;

public interface RoomService {
	
	public Page<Room> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public Optional<Room> findById(Long id);
	
	public Room findByName(String roomName);

	public Page<Room> findByBranchId(Long id, RequestParameter requestParameter);
	
	public Room save(Room room);

	public void deleteById(Long id);

}

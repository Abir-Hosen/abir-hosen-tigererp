package com.tigerslab.tigererp.repository.org;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.org.Room;

@Repository("roomRepository")
public interface RoomRepository extends PagingAndSortingRepository<Room, Long>{

	@Query("select r from Room r where r.roomName like %?1% or r.id like %?1%")
	Page<Room> findAllBySortAndOrder(String filter, Pageable pageable);
	
	Room findByRoomName(String roomName);
	
	Page<Room> findByBranchId(Long id, Pageable pageable);

}

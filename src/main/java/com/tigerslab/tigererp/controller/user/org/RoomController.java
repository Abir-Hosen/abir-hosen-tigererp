package com.tigerslab.tigererp.controller.user.org;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.Branch;
import com.tigerslab.tigererp.model.org.Room;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.service.org.BranchService;
import com.tigerslab.tigererp.service.org.RoomService;

@Transactional
@RestController
@RequestMapping("/api/room")
public class RoomController {
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private BranchService branchService;

	@Autowired
	AccessPermission accessPermission;
	
	private Logger logger = LoggerFactory.getLogger(RoomController.class);
	
	@GetMapping
	public ResponseEntity<Page<Room>> findAllBySortAndOrder(RequestParameter requestParameter, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadRoomSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET method");
		Page<Room> page = roomService.findAllBySortAndOrder(requestParameter);
		return ResponseEntity.ok(page);
	}
	
	@PostMapping
	public ResponseEntity<Room> create(@RequestBody @Valid Room room, BindingResult bindingResult, HttpSession session) {
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getCreateRoomSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering POST Method");
		StringBuilder serverSideErrors = new StringBuilder("");
		if(bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error : errorList) {
				serverSideErrors.append(", ").append(error.getDefaultMessage());
			}
			logger.error("Form has errors: "+serverSideErrors);
			return ResponseEntity.badRequest().build();
		}
		else {
			long id = room.getBranch().getId();
			Optional<Branch> branch = branchService.findById(id);
			if(branch.isPresent()) {
				room.setBranch(branch.get());
				roomService.save(room);
				return ResponseEntity.ok(room);
			}else {
				return ResponseEntity.badRequest().build();
			}
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Room> findById(@PathVariable Long id, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadRoomSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET findById");
		Optional<Room> SingleRoom = roomService.findById(id);
		if(!SingleRoom.isPresent()) {
			logger.info("Id "+id+" is not existed");
			ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(SingleRoom.get());
	}
	
	@GetMapping("branch/{id}")
	public ResponseEntity<Page<Room>> findByBranchId(@PathVariable Long id, RequestParameter requestParameter, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadRoomSettings()) {
			return ResponseEntity.badRequest().build();
		}
		logger.info("Entering GET method");
		Page<Room> page = roomService.findByBranchId(id, requestParameter);
		return ResponseEntity.ok(page);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Room> update(@PathVariable Long id, @RequestBody @Valid Room room, BindingResult bindingResult, HttpSession session){
		
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getUpdateRoomSettings()) {
			return ResponseEntity.badRequest().build();
		}
		
		logger.info("Entering PUT Method");
		logger.info("Room Info: "+room);
		StringBuilder serverSideErrors = new StringBuilder("");
		if(bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error : errorList) {
				serverSideErrors.append(", ").append(error.getDefaultMessage());
			}
			logger.error("Form has errors: "+serverSideErrors);
			return ResponseEntity.badRequest().build();
		}else {
			if(!roomService.findById(id).isPresent()) {
				logger.info("Id "+id+" is not existed");
				return ResponseEntity.badRequest().build();
			}else {
				long bId = room.getBranch().getId();
				Optional<Branch> branch = branchService.findById(bId);
				if(branch.isPresent()) {
					room.setBranch(branch.get());
					roomService.save(room);
					return ResponseEntity.ok(room);
				}else {
					return ResponseEntity.badRequest().build();
				}
			}
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Room> delete(@PathVariable Long id, HttpSession session){
		logger.info("Entering DELETE Method to Delete Room Id: "+id);
		accessPermission = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getDeleteRoomSettings()) {
			logger.info("Access denied for this operation");
			return ResponseEntity.badRequest().build();
		}
		if(!roomService.findById(id).isPresent()){
			logger.info("Id "+id+" is not existed");
			return ResponseEntity.badRequest().build();
		}
		roomService.deleteById(id);
		
		return ResponseEntity.ok(null);
	}

}

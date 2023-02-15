package com.tigerslab.tigererp.controller.user;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.User;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.repository.user.UserRepository;
import com.tigerslab.tigererp.service.user.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	AccessPermission accessPermission;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@PutMapping("/updatePassword")
	public ResponseEntity<User> updateUserPasswordOnly(@RequestBody @Valid User user, BindingResult bindingResult, HttpSession httpSession){
		if(!bindingResult.hasErrors()) {
			User sessionUser = userService.findUserByEmail((String) httpSession.getAttribute("userName"));
			System.out.println("Session User: "+sessionUser);
			if(sessionUser !=null) {
				if(encoder.matches(user.getPassword(), sessionUser.getPassword())) {
					User updateUser = new User();
					updateUser = sessionUser;
					updateUser.setPassword(encoder.encode(user.getNewPassword()));
					System.out.println("NEW USR: "+updateUser);
					userService.save(updateUser);
					return ResponseEntity.ok(user);
				}else {
					return ResponseEntity.badRequest().build();
				}
			}else {
				return ResponseEntity.badRequest().build();
			}
		}else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/updateEmailPassword")
	public ResponseEntity<User> updateUserEmailPassword(@RequestBody @Valid User user, BindingResult bindingResult, HttpSession httpSession){

		accessPermission = (AccessPermission) httpSession.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(!accessPermission.getReadProfitLossStatementSettings()) {
			return ResponseEntity.badRequest().build();
		}
		
		if(!bindingResult.hasErrors()) {
			User sessionUser = userService.findUserByEmail((String) httpSession.getAttribute("userName"));
			
			//Here Should be a check for administration or managing director
					//--------->>>> 
//			if(sessionUser.getRoles().iterator().next().getId()!=3) {
//				return ResponseEntity.badRequest().build();
//			}
			if(sessionUser.getEmpRoles().iterator().next().getId()!=1) {
				return ResponseEntity.badRequest().build();
			}
			//Here Should be a check for administration or managing director
			User beUpdatedUser = userService.findUserByEmail(user.getEmail());
			
			System.out.println("Session User: "+sessionUser);
			System.out.println("beUpdatedUser User: "+beUpdatedUser);
			if(beUpdatedUser !=null) {
				User updateUser = new User();
				updateUser = beUpdatedUser;
				updateUser.setPassword(encoder.encode(user.getNewPassword()));
				System.out.println("NEW USR: "+updateUser);
				userService.save(updateUser);
				return ResponseEntity.ok(user);
			}else {
				return ResponseEntity.badRequest().build();
			}
		}else {
			return ResponseEntity.badRequest().build();
		}
	}

}
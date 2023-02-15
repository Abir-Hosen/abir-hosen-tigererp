package com.tigerslab.tigererp.controller.user;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.User;
import com.tigerslab.tigererp.service.user.UserService;

@Controller
public class SignupController {
	
	@Autowired
	private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(SignupController.class);
	
	@RequestMapping(value="/signup", method = RequestMethod.GET)
	public String showSignup() {
		return "signup";
	}
	
	@RequestMapping(value="/signup", method = RequestMethod.POST)
	public String createUser(@Valid User user, BindingResult bindingResult) {
		logger.info("------------------- "+user);
		User userExist = userService.findUserByEmail(user.getEmail().trim().toLowerCase());
	    if(userExist != null) {
	    	bindingResult.rejectValue("email", "error.user", "This user already exist");
	    }
	    if(bindingResult.hasErrors()) {
	    	return "signup";
	    }
	    else {
	    	
	    	userService.saveUser(user, ConstantFactory.ROLE_CUSTOMER);
	    	return "redirect:login?registration_success=true";
	    }
	}
	

}

package com.tigerslab.tigererp.controller;

import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tigerslab.tigererp.helper.UserInfo;
import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;
import com.tigerslab.tigererp.service.user.employee.AccessPermissionService;

@Controller
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private AccessPermissionService accessPermissionService;
	
	@Autowired
	private UserInfo userInfo;
	
	@RequestMapping(value="/login")
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping(value="/welcome")
	public String checkUserType(SecurityContextHolder securityContext, HttpSession session) {
		String role = "";
		Authentication authentication = securityContext.getContext().getAuthentication();
		String userName = authentication.getName();
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
		for(int i=0; i<authorities.size(); i++) {
			role = authorities.toArray()[i]+"";
		}
		session.setAttribute("userName", userName);
		session.setAttribute("role", role);
		Optional<AccessPermission> accessPermission = accessPermissionService.getLoggegUserAccessPermission(session);
		System.out.println("Sesssion to be setted");
		if(accessPermission!=null) {
			System.out.println("Sesssion got setted");
			session.setAttribute(ConstantFactory.ACCESS_PERMISSION, accessPermission.get());
		}
		
		if(role.equals(ConstantFactory.ROLE_ADMIN)) {
			logger.info("Redirecting to Admin Dashboard");
			return "redirect:/admin";
		}
		else if(role.equals(ConstantFactory.ROLE_EMPLOYEE)){
			logger.info("Redirecting to Employee Dashboard");
			return "redirect:/employee";
		}
		else if(role.equals(ConstantFactory.ROLE_CUSTOMER)){
			logger.info("Redirecting to Customer Dashboard");
			return "redirect:/customer";
		}

		return "";
	}

}

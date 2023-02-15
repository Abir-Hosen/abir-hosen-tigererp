package com.tigerslab.tigererp.helper;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tigerslab.tigererp.controller.LoginController;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.service.user.employee.AccessPermissionService;

@Component
public class UserInfo {
	
	private AccessPermission loggedUserAccessPermission;
	private Logger logger = LoggerFactory.getLogger(UserInfo.class);
	
	private String userName;

	
	@Autowired
	private AccessPermissionService accessPermissionService;
	
	public AccessPermission getLoggedUserAccessPermission() {
		Optional<AccessPermission> accessPermissionCheck = accessPermissionService.findByEmployeeRoleUserEmail(this.userName);
		if(!accessPermissionCheck.isPresent()) {
			logger.info("Id "+userName+" is not existed");
		}
		else {
			this.loggedUserAccessPermission = accessPermissionCheck.get();
		}
		return this.loggedUserAccessPermission;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	

}

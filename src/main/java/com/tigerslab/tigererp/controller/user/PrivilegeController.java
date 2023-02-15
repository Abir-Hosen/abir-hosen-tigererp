package com.tigerslab.tigererp.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tigerslab.tigererp.controller.AbstractController;
import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.Role;
import com.tigerslab.tigererp.model.User;
import com.tigerslab.tigererp.model.httphelper.ResponseMetadata;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;
import com.tigerslab.tigererp.service.user.RoleService;
import com.tigerslab.tigererp.service.user.UserService;
import com.tigerslab.tigererp.service.user.employee.AccessPermissionService;

@Controller
public class PrivilegeController extends AbstractController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccessPermissionService accessPermissionService;
	
	@RequestMapping(value="/api/privileges", method=RequestMethod.GET)
	public @ResponseBody Map getPrivileges(HttpSession session) {
		AccessPermission accessPermissionSession = (AccessPermission) session.getAttribute(ConstantFactory.ACCESS_PERMISSION);
		if(accessPermissionSession != null) {
			accessPermissionSession.setEmployeeRole(null);
		}
		
		Map response = new HashMap();
		ResponseMetadata metaData = new ResponseMetadata();
		if(accessPermissionSession != null) {
			metaData.setStatus(ConstantFactory.SUCCESS);
			response.put("metaData", metaData);
			response.put("content", accessPermissionSession);
			return response;
		}
		
		return response;
	}

}

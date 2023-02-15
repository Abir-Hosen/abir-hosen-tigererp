package com.tigerslab.tigererp.controller.user.employee;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tigerslab.tigererp.controller.AbstractController;

@Controller
public class EmployeeDashboardController extends AbstractController {
	
	@RequestMapping(value="/employee")
	public String showEmployeeDashboard() {
		return "/employee/index";
	}

}

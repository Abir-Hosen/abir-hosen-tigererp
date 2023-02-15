package com.tigerslab.tigererp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tigerslab.tigererp.controller.AbstractController;

@Controller
public class AdminDashboardController extends AbstractController {
	
	@RequestMapping(value="/admin")
	public String showEmployeeDashboard() {
		return "/admin/index";
	}

}

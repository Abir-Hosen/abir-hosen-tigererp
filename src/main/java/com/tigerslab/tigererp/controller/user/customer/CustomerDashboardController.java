package com.tigerslab.tigererp.controller.user.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tigerslab.tigererp.controller.AbstractController;

@Controller
public class CustomerDashboardController extends AbstractController {
	
	@RequestMapping(value="/customer")
	public String showEmployeeDashboard() {
		return "/customer/index";
	}

}

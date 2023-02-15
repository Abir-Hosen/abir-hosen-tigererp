package com.tigerslab.tigererp.controller.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tigerslab.tigererp.controller.LoginController;

@Controller
public class ErrorControllerTigerERP implements ErrorController {
	
	private Logger logger = LoggerFactory.getLogger(ErrorControllerTigerERP.class);

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    logger.debug("Called /error URL. Error begins.");
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	    
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	        	logger.debug("Error 404 occured");
	            return "error/error-404";
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	        	logger.debug("Error 500 occured");
	            return "error/error-500";
	        }
	    }
	    logger.debug("Can not trace any HTTP status code and error. Global error page call");
	    return "error/error";
	}

}

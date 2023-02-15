package com.tigerslab.tigererp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractController {
	
	private Logger logger = LoggerFactory.getLogger(AbstractController.class);
	
	public AbstractController() {
		logger.info("Call Abstract Controller");
	}

}

package com.tigerslab.tigererp.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.tigerslab.tigererp.model.ConstantFactory;

public class FileUploadUtility {
	
	private static String REAL_PATH = "";

	private static final Logger logger = LoggerFactory.getLogger(FileUploadUtility.class);
	
	public static void uploadFile(HttpSession httpSession, MultipartFile file, String imgUrl) {
		

		// get the real server path
		REAL_PATH = httpSession.getServletContext().getRealPath(ConstantFactory.UPLOAD_PATH);

		
		logger.info(REAL_PATH);	
		
		if(!new File(REAL_PATH).exists()) {
			new File(REAL_PATH).mkdirs();
		}
		

		try {
			//transfer the file to both the location
			file.transferTo(new File(REAL_PATH + imgUrl + ".jpg"));
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}

}
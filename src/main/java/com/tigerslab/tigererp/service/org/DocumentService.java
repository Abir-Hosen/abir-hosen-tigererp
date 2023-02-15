package com.tigerslab.tigererp.service.org;

import java.io.IOException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.Document;

public interface DocumentService {
	
	public Page<Document> findAllBySortAndOrder(RequestParameter requestParameter);
	
	public Optional<Document> findById(Long id);
	
	public Document findByDocumentName(String documentName);
	
	public Document save(Document document);

	public void deleteById(Long id);

	Document save(MultipartFile file, Document document) throws IOException;
	
//    ResponseMetadata save(MultipartFile multipartFile) throws IOException;
//
//    byte[] getDocumentFile(Long id);
//
//    List<Document> findAll();
}

package com.tigerslab.tigererp.service.org;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.Document;
import com.tigerslab.tigererp.repository.org.DocumentRepository;

@Service("documentService")
public class DocumentServiceImpl implements DocumentService {

	Logger logger = LoggerFactory.getLogger(DocumentService.class);

	@Autowired
	DocumentRepository repository;

	private Pageable pagable;

	@Override
	public Page<Document> findAllBySortAndOrder(RequestParameter requestParameter) {
		logger.info("Entering findAllBySortAndOrder Method");
		if(requestParameter.getPage() >= 1) {
			requestParameter.setPage(requestParameter.getPage()-1);
		}
		if(requestParameter.getOrder().charAt(0) == '-') {
			String sortOrder = requestParameter.getOrder().substring(1);
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParameter.getPage(), requestParameter.getLimit(), Sort.by(requestParameter.getOrder()).ascending());
		}
		return repository.findAllBySortAndOrder(requestParameter.getFilter(), pagable);
	}

	@Override
	public Optional<Document> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Document findByDocumentName(String documentName) {
		return repository.findByDocumentName(documentName);
	}

	@Override
	public Document save(MultipartFile file, Document document) throws IOException {
//		Document doc = new Document();
//		doc.setDocumentFormalName(document.getDocumentFormalName());
//		doc.setDocumentName(file.getOriginalFilename());
//		doc.setFile(file.getBytes());
//		repository.save(doc);
		
		return document;
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Document save(Document document) {
		return repository.save(document);
	}

}

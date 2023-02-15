package com.tigerslab.tigererp.repository.org;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tigerslab.tigererp.model.org.Branch;
import com.tigerslab.tigererp.model.org.Document;

@Repository("documentRepository")
public interface DocumentRepository extends PagingAndSortingRepository<Document, Long> {

	@Query("select b from Document b where b.documentName like %?1% or b.id like %?1%")
	Page<Document> findAllBySortAndOrder(String documentName, Pageable pageable);
	
	public Document findByDocumentName(String documentName);

}

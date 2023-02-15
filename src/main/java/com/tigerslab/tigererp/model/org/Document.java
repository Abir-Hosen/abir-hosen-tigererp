package com.tigerslab.tigererp.model.org;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.tigerslab.tigererp.model.AbstractData;

@Entity
@Table(name="document")
public class Document extends AbstractData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documentIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "documentIdGenerator", sequenceName = "documentSeq")
	private long id;
	
	@Column
	private String documentName;
	
	@Column
	private String documentFormalName;
	
	@Column
	private String documentFullPath;
	
	@Transient
	private MultipartFile file;

	public Document() {
		super();
		this.documentFormalName = "IMG"+ UUID.randomUUID().toString().substring(26).toUpperCase();
	}

	public Document(long id, String documentName, String documentFormalName, String documentFullPath,
			MultipartFile file) {
		super();
		this.id = id;
		this.documentName = documentName;
		this.documentFormalName = documentFormalName;
		this.documentFullPath = documentFullPath;
		this.file = file;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentFormalName() {
		return documentFormalName;
	}

	public void setDocumentFormalName(String documentFormalName) {
		this.documentFormalName = documentFormalName;
	}

	public String getDocumentFullPath() {
		return documentFullPath;
	}

	public void setDocumentFullPath(String documentFullPath) {
		this.documentFullPath = documentFullPath;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", documentName=" + documentName + ", documentFormalName=" + documentFormalName
				+ ", documentFullPath=" + documentFullPath + ", file=" + file + "]";
	}
}

package com.tigerslab.tigererp.model.init;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ApplicationInfo")
public class ApplicationInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
	@SequenceGenerator(initialValue = 1, name = "idgenerator", sequenceName = "applicationInfoSeq")
	private Long id;
	
	@Column(name="isInstalled")
	private Boolean isInstalled;
	
	public ApplicationInfo() {
		super();
	}

	public ApplicationInfo(Long id, Boolean isInstalled) {
		super();
		this.id = id;
		this.isInstalled = isInstalled;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsInstalled() {
		return isInstalled;
	}

	public void setIsInstalled(Boolean isInstalled) {
		this.isInstalled = isInstalled;
	}

	@Override
	public String toString() {
		return "ApplicationInfo [id=" + id + ", isInstalled=" + isInstalled + "]";
	}
	
}

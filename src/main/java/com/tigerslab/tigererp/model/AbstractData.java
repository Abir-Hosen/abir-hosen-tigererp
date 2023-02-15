package com.tigerslab.tigererp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class AbstractData implements Serializable {
	
	@Column(name = "isDeletable", columnDefinition="bit default true", nullable = false)
	private Boolean isDeletable = true;
	
	@Column(name = "isUpdateAble", columnDefinition="bit default true", nullable = false)
	private Boolean isUpdateAble = true;
	
	@Column(name="createdAt")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="updatedAt")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	public AbstractData() {}

	public Boolean getIsDeletable() {
		return isDeletable;
	}

	public void setIsDeletable(Boolean isDeletable) {
		this.isDeletable = isDeletable;
	}

	public Boolean getIsUpdateAble() {
		return isUpdateAble;
	}

	public void setIsUpdateAble(Boolean isUpdateAble) {
		this.isUpdateAble = isUpdateAble;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PrePersist
	protected void onCreate() {
	    createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
	   updatedAt = new Date();
	}

}

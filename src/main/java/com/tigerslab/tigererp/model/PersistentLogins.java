package com.tigerslab.tigererp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="persistent_logins")
public class PersistentLogins {
	
	@NotNull
	@Column(length = 100)
	private String username;
	
	@Id
	@Column(length = 64)
	private String series;
	
	@NotNull
	@Column(length = 64)
	private String token;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUsed;

	public String getUsername() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}
	

}

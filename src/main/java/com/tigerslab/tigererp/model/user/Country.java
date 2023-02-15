package com.tigerslab.tigererp.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tigerslab.tigererp.model.AbstractData;

@Entity
@Table(name="country")
public class Country extends AbstractData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countryIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "countryIdGenerator", sequenceName = "countrySeq")
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="niceName")
	private String niceName;
	
	@Column(name="countryCode")
	private String countryCode;
	
	@Column(name="phoneCode")

	private int phoneCode;
	
	@Column(name="iso3")
	private String iso3;
	
	@Column(name="numCode")
	private String numCode;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNiceName() {
		return niceName;
	}

	public void setNiceName(String niceName) {
		this.niceName = niceName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public int getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(int phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getIso3() {
		return iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}



	public String getNumCode() {
		return numCode;
	}

	public void setNumCode(String numCode) {
		this.numCode = numCode;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + ", niceName=" + niceName + ", countryCode=" + countryCode
				+ ", phoneCode=" + phoneCode + ", iso3=" + iso3 + ", numCode=" + numCode + "]";
	}
	
}

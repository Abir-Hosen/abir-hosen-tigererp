package com.tigerslab.tigererp.model.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tigerslab.tigererp.model.AbstractData;

@Entity
@Table(name="PhoneFormatFullCountry")
public class PhoneFormatFullCountry extends AbstractData{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phoneFormatIdgenerator")
	@SequenceGenerator(initialValue = 1, name = "phoneFormatIdgenerator", sequenceName = "phoneFormatIdSeq")
	private Long id;
	
	@Column(name="number")
	private String number;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "country")
	private Country country;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "PhoneFormatFullCountry [id=" + id + ", number=" + number + ", country=" + country + "]";
	}
	

}

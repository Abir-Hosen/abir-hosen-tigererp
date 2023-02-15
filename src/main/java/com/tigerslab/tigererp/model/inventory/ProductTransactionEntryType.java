package com.tigerslab.tigererp.model.inventory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ProductTransactionType")
public class ProductTransactionEntryType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ProductTransactionEntryTypeIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "ProductTransactionEntryTypeIdGenerator", sequenceName = "ProductTransactionEntryTypeSeq")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="sign")
	private String sign;
	
	public ProductTransactionEntryType() {
		
	}
	
	public ProductTransactionEntryType(Long id, String name, String sign) {
		this.id = id;
		this.name = name;
		this.sign = sign;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	

}

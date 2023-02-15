package com.tigerslab.tigererp.model.financial;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TransactionEntryType")
public class TransactionEntryType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TransactionEntryTypeIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "TransactionEntryTypeIdGenerator", sequenceName = "TransactionEntryTypeSeq")
	private Long id;
	
	private String name;

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

	@Override
	public String toString() {
		return "TransactionEntryType [id=" + id + ", name=" + name + "]";
	}
	

}

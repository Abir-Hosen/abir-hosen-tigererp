package com.tigerslab.tigererp.model.financial;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ledgergroup")
public class LedgerGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ledgergroupIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "ledgergroupIdGenerator", sequenceName = "ledgergroupSeq")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "alias")
	private String alias;
	@Column(name = "description")
	private String description;
	@Column(name = "ledgerIndex")
	private Long ledgerIndex;
	
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
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getLedgerIndex() {
		return ledgerIndex;
	}
	public void setLedgerIndex(Long ledgerIndex) {
		this.ledgerIndex = ledgerIndex;
	}
	@Override
	public String toString() {
		return "LedgerGroup [id=" + id + ", name=" + name + ", alias=" + alias + ", description=" + description
				+ ", ledgerIndex=" + ledgerIndex + "]";
	}
}

package com.tigerslab.tigererp.model.org.inventory;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Table
@Entity(name = "Units")
public class Units {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unitsidgenerator")
	@SequenceGenerator(initialValue = 1, name = "unitsidgenerator", sequenceName = "unitsSeq")
	private Long id;
	
	private String symbol;
	
	private String formalName;
	
	private String description;
	
	private Integer numberOfDecimalPlaces;
	
	private Double value;
	
//  @ManyToOne(cascade={CascadeType.ALL})
//  @JoinColumn(name="first_unit")
//	private long firstUnit;
  
//  @ManyToOne(cascade={CascadeType.ALL})
//  @JoinColumn(name="second_unit")
//	private long secondUnit;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="first_unit", nullable = true)
    @JsonIgnoreProperties("measerFirstUnit")
    private Units firstUnit;
    
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="second_unit",  nullable = true)
    @JsonIgnoreProperties("measerSecondUnit")
    private Units secondUnit;

    @OneToMany(mappedBy="firstUnit", cascade=CascadeType.MERGE)
    @JsonIgnoreProperties("firstUnit")
    private Set<Units> measerFirstUnit = new HashSet<Units>();
    
    @OneToMany(mappedBy="secondUnit", cascade=CascadeType.MERGE)
    @JsonIgnoreProperties("secondUnit")
    private Set<Units> measerSecondUnit = new HashSet<Units>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getFormalName() {
		return formalName;
	}

	public void setFormalName(String formalName) {
		this.formalName = formalName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumberOfDecimalPlaces() {
		return numberOfDecimalPlaces;
	}

	public void setNumberOfDecimalPlaces(Integer numberOfDecimalPlaces) {
		this.numberOfDecimalPlaces = numberOfDecimalPlaces;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Units getFirstUnit() {
		return firstUnit;
	}

	public void setFirstUnit(Units firstUnit) {
		this.firstUnit = firstUnit;
	}

	public Units getSecondUnit() {
		return secondUnit;
	}

	public void setSecondUnit(Units secondUnit) {
		this.secondUnit = secondUnit;
	}

	public Set<Units> getMeaserFirstUnit() {
		return measerFirstUnit;
	}

	public void setMeaserFirstUnit(Set<Units> measerFirstUnit) {
		this.measerFirstUnit = measerFirstUnit;
	}

	public Set<Units> getMeaserSecondUnit() {
		return measerSecondUnit;
	}

	public void setMeaserSecondUnit(Set<Units> measerSecondUnit) {
		this.measerSecondUnit = measerSecondUnit;
	}

	@Override
	public String toString() {
		return "Units: "+value;
	}
}

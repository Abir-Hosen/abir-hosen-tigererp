package com.tigerslab.tigererp.model.org;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tigerslab.tigererp.model.AbstractData;
import com.tigerslab.tigererp.model.user.Address;

@Entity
@Table(name="company")
public class Company extends AbstractData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comapnyIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "comapnyIdGenerator", sequenceName = "companySeq")
	private long id;
	
	@Column(name="companyName")
	private String companyName;
	
	@Column(name="foundedDate")
	@Temporal(TemporalType.DATE)
	private Date foundedDate;
	
	@Column(name="propriotorName")
	@NotBlank
	@Size(min = 3, max = 255)
	private String propriotorName;
	
	@Column(name="financialYearStartsFrom")
	@Temporal(TemporalType.DATE)
	private Date financialYearStartsFrom;
	
	@Column(name="registrationNo")
	@Size(max = 255)
	private String registrationNo;
	
	@Column(name="vatNo")
	@Size(max = 255)
	private String vatNo;
	
	@Column(name="baseCurrencySymbol")
	@NotBlank
	@Size(min = 1, max = 10)
	private String baseCurrencySymbol;
	
	@Column(name="formalCurrencyName")
	@Size(min = 2, max = 255)
	private String formalCurrencyName;
	
	@Column(name="currencySymbolSuffixToAmount")
	private boolean currencySymbolSuffixToAmount;
	
	@Column(name="addSpaceBetweenSymbolAndAmount")
	private boolean addSpaceBetweenSymbolAndAmount;
	
	@Column(name="showAmountInMillion")
	private boolean showAmountInMillion;
	
	@Column(name="numberOfDecimalPlaces")
	private byte numberOfDecimalPlaces;
	
	@Column(name="wordDefineForDecimalNumber")
	@Size(min = 2, max = 255)
	private String wordDefineForDecimalNumber;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) 
	@JoinTable(name = "companyPhysicalAddress", joinColumns = @JoinColumn(name = "companyId"), inverseJoinColumns = @JoinColumn(name = "addressId"))
	private Set<Address> companyPhysicalAddress;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn
	private PaperType paperType;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getFoundedDate() {
		return foundedDate;
	}

	public void setFoundedDate(Date foundedDate) {
		this.foundedDate = foundedDate;
	}

	public String getPropriotorName() {
		return propriotorName;
	}

	public void setPropriotorName(String propriotorName) {
		this.propriotorName = propriotorName;
	}

	public Date getFinancialYearStartsFrom() {
		return financialYearStartsFrom;
	}

	public void setFinancialYearStartsFrom(Date financialYearStartsFrom) {
		this.financialYearStartsFrom = financialYearStartsFrom;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getVatNo() {
		return vatNo;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}

	public String getBaseCurrencySymbol() {
		return baseCurrencySymbol;
	}

	public void setBaseCurrencySymbol(String baseCurrencySymbol) {
		this.baseCurrencySymbol = baseCurrencySymbol;
	}

	public String getFormalCurrencyName() {
		return formalCurrencyName;
	}

	public void setFormalCurrencyName(String formalCurrencyName) {
		this.formalCurrencyName = formalCurrencyName;
	}

	public boolean isCurrencySymbolSuffixToAmount() {
		return currencySymbolSuffixToAmount;
	}

	public void setCurrencySymbolSuffixToAmount(boolean currencySymbolSuffixToAmount) {
		this.currencySymbolSuffixToAmount = currencySymbolSuffixToAmount;
	}

	public boolean isAddSpaceBetweenSymbolAndAmount() {
		return addSpaceBetweenSymbolAndAmount;
	}

	public void setAddSpaceBetweenSymbolAndAmount(boolean addSpaceBetweenSymbolAndAmount) {
		this.addSpaceBetweenSymbolAndAmount = addSpaceBetweenSymbolAndAmount;
	}

	public boolean isShowAmountInMillion() {
		return showAmountInMillion;
	}

	public void setShowAmountInMillion(boolean showAmountInMillion) {
		this.showAmountInMillion = showAmountInMillion;
	}

	public byte getNumberOfDecimalPlaces() {
		return numberOfDecimalPlaces;
	}

	public void setNumberOfDecimalPlaces(byte numberOfDecimalPlaces) {
		this.numberOfDecimalPlaces = numberOfDecimalPlaces;
	}

	public String getWordDefineForDecimalNumber() {
		return wordDefineForDecimalNumber;
	}

	public void setWordDefineForDecimalNumber(String wordDefineForDecimalNumber) {
		this.wordDefineForDecimalNumber = wordDefineForDecimalNumber;
	}

	public Set<Address> getCompanyPhysicalAddress() {
		return companyPhysicalAddress;
	}

	public void setCompanyPhysicalAddress(Set<Address> companyPhysicalAddress) {
		this.companyPhysicalAddress = companyPhysicalAddress;
	}

	public PaperType getPaperType() {
		return paperType;
	}

	public void setPaperType(PaperType paperType) {
		this.paperType = paperType;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName + ", foundedDate=" + foundedDate
				+ ", propriotorName=" + propriotorName + ", financialYearStartsFrom=" + financialYearStartsFrom
				+ ", registrationNo=" + registrationNo + ", vatNo=" + vatNo + ", baseCurrencySymbol="
				+ baseCurrencySymbol + ", formalCurrencyName=" + formalCurrencyName + ", currencySymbolSuffixToAmount="
				+ currencySymbolSuffixToAmount + ", addSpaceBetweenSymbolAndAmount=" + addSpaceBetweenSymbolAndAmount
				+ ", showAmountInMillion=" + showAmountInMillion + ", numberOfDecimalPlaces=" + numberOfDecimalPlaces
				+ ", wordDefineForDecimalNumber=" + wordDefineForDecimalNumber + ", companyPhysicalAddress="
				+ companyPhysicalAddress + ", paperType=" + paperType + "]";
	}
	
}

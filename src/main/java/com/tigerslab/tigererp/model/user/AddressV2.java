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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.tigerslab.tigererp.model.AbstractData;

@Entity
@Table(name="addressV2")
public class AddressV2 extends AbstractData{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addressV2IdGenerator")
	@SequenceGenerator(initialValue = 1, name = "addressV2IdGenerator", sequenceName = "addressV2Seq")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="contactPersonName")
	@NotBlank
	private String contactPersonName;
	
	@NotBlank
	@Size(min = 1, max = 255)
	private String addressLine1;
	
	private String addressLine2;
	
	@NotBlank
	@Size(min = 1, max = 255)
	private String city;
	
	private String zip;
	
	@Column(unique = true)
	private String email;
	
	private String website;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "addressCountry")
	private Country addressCountry;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "phone")
	private PhoneFormatFullCountry phone;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fax")
	private PhoneFormatFullCountry fax;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "mobilePhone")
	private PhoneFormatFullCountry mobilePhone;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "altMobilePhone")
	private PhoneFormatFullCountry altMobilePhone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Country getAddressCountry() {
		return addressCountry;
	}

	public void setAddressCountry(Country addressCountry) {
		this.addressCountry = addressCountry;
	}

	public PhoneFormatFullCountry getPhone() {
		return phone;
	}

	public void setPhone(PhoneFormatFullCountry phone) {
		this.phone = phone;
	}

	public PhoneFormatFullCountry getFax() {
		return fax;
	}

	public void setFax(PhoneFormatFullCountry fax) {
		this.fax = fax;
	}

	public PhoneFormatFullCountry getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(PhoneFormatFullCountry mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public PhoneFormatFullCountry getAltMobilePhone() {
		return altMobilePhone;
	}

	public void setAltMobilePhone(PhoneFormatFullCountry altMobilePhone) {
		this.altMobilePhone = altMobilePhone;
	}

	@Override
	public String toString() {
		return "AddressV2 [id=" + id + ", contactPersonName=" + contactPersonName + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", city=" + city + ", zip=" + zip + ", email=" + email
				+ ", website=" + website + ", addressCountry=" + addressCountry + ", phone=" + phone + ", fax=" + fax
				+ ", mobilePhone=" + mobilePhone + ", altMobilePhone=" + altMobilePhone + "]";
	}

}

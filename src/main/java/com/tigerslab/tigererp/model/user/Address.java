package com.tigerslab.tigererp.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="address")
public class Address extends AbstractData{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addressIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "addressIdGenerator", sequenceName = "addressSeq")
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	@Column(name="contactPersonName")
	@NotBlank
	@Size(min = 3, max = 255)
	private String contactPersonName;
	
	@NotBlank
	@Size(min = 3, max = 255)
	private String addressLine1;
	
	private String addressLine2;
	
	@NotBlank
	@Size(min = 3, max = 255)
	private String city;
	
	private String zip;
	
	private String phone;
	
	private String fax;
	
	@NotBlank
	@Size(min = 5, max = 50)
	private String mobilePhone;
	
	private String altMobilePhone;
	
	private String email;
	
	private String website;
	
	@OneToOne
	@JoinColumn(name = "addressCountry")
	private Country addressCountry;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getAltMobilePhone() {
		return altMobilePhone;
	}

	public void setAltMobilePhone(String altMobilePhone) {
		this.altMobilePhone = altMobilePhone;
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

	@Override
	public String toString() {
		return "Address [id=" + id + ", contactPersonName=" + contactPersonName + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", city=" + city + ", zip=" + zip + ", phone=" + phone + ", fax="
				+ fax + ", mobilePhone=" + mobilePhone + ", altMobilePhone=" + altMobilePhone + ", email=" + email
				+ ", website=" + website + ", addressCountry=" + addressCountry + "]";
	}

}

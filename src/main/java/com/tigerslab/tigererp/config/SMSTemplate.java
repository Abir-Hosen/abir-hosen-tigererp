package com.tigerslab.tigererp.config;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tigerslab.tigererp.model.org.Company;
import com.tigerslab.tigererp.repository.org.CompanyProfileRepository;
import com.tigerslab.tigererp.service.org.CompanyProfileService;

@Component
public class SMSTemplate {
	
	private String ledgerAccountCreationTemplate = "";
	private String ledgerAccountCreationTemplateSufix = " এর সঙ্গে থাকার জন্য আপনাকে ধন্যবাদ। বিস্তারিত জানতে ";
	private Company companyInfo;
	
	@Autowired
	private CompanyProfileService companyProfileService;
	
	@Autowired
	private CompanyProfileRepository companyProfileRepository;
	
	@PostConstruct
	public void initBean() {

	}

	public String getLedgerAccountCreationTemplate() {
		Optional<Company> companyCheck = companyProfileRepository.findById((Long.valueOf(1)));
		Company company = null;
		if(companyCheck.isPresent()) {
			 company = companyCheck.get();
			 this.companyInfo = companyCheck.get();
		}
		
		if(company != null) {
			String companyName = company.getCompanyName().trim();
			if(companyName.length() > 89) {
				companyName = companyName.substring(0, 88);
			}
			String countryCode = "";
			countryCode = "%2B"+String.valueOf(company.getCompanyPhysicalAddress().iterator().next().getAddressCountry().getPhoneCode());
			String mobileNumber = company.getCompanyPhysicalAddress().iterator().next().getMobilePhone();
			
			this.ledgerAccountCreationTemplate = "";
			this.ledgerAccountCreationTemplate = this.ledgerAccountCreationTemplate.concat(companyName).concat(this.ledgerAccountCreationTemplateSufix).concat(countryCode).concat(mobileNumber);
			System.out.println("FROM templates"+this.ledgerAccountCreationTemplate);
		}
		return this.ledgerAccountCreationTemplate;
	}

	public void setLedgerAccountCreationTemplate(String ledgerAccountCreationTemplate) {
		this.ledgerAccountCreationTemplate = ledgerAccountCreationTemplate;
	}

	public String getLedgerAccountCreationTemplateSufix() {
		return ledgerAccountCreationTemplateSufix;
	}

	public void setLedgerAccountCreationTemplateSufix(String ledgerAccountCreationTemplateSufix) {
		this.ledgerAccountCreationTemplateSufix = ledgerAccountCreationTemplateSufix;
	}

	public CompanyProfileService getCompanyProfileService() {
		return companyProfileService;
	}

	public void setCompanyProfileService(CompanyProfileService companyProfileService) {
		this.companyProfileService = companyProfileService;
	}

}

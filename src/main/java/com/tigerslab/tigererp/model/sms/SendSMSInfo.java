package com.tigerslab.tigererp.model.sms;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tigerslab.tigererp.model.AbstractData;
import com.tigerslab.tigererp.model.financial.LedgerAccounts;

@Entity
@Table(name="SendSmsInfo")
public class SendSMSInfo extends AbstractData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sendsmsidgenerator")
	@SequenceGenerator(initialValue = 1, name = "sendsmsidgenerator", sequenceName = "sendSMSSeq")
	private long id;
	
	@Column(name = "sendNumber")
	private String sendNumber;
	
	@Column(name = "smsBody")
	private String smsBody;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="ledgerAccountId")
	private LedgerAccounts ledgerAccounts;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSendNumber() {
		return sendNumber;
	}

	public void setSendNumber(String sendNumber) {
		this.sendNumber = sendNumber;
	}

	public String getSmsBody() {
		return smsBody;
	}

	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}

	public LedgerAccounts getLedgerAccounts() {
		return ledgerAccounts;
	}

	public void setLedgerAccounts(LedgerAccounts ledgerAccounts) {
		this.ledgerAccounts = ledgerAccounts;
	}

	@Override
	public String toString() {
		return "SendSMS [id=" + id + ", sendNumber=" + sendNumber + ", smsBody=" + smsBody + ", ledgerAccounts="
				+ ledgerAccounts + "]";
	}

}

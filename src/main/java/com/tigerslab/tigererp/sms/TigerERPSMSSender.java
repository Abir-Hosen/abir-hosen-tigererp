package com.tigerslab.tigererp.sms;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.financial.LedgerAccounts;
import com.tigerslab.tigererp.model.sms.SendSMSInfo;
import com.tigerslab.tigererp.service.sms.SendSMSInfoService;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class TigerERPSMSSender{
	
	@Value("${tigererp.smsusername}")
	private String userName;
	
	@Value("${tigererp.smsuserpassword}")
	private String password;
	
	@Value("${tigererp.smsgatewayurl}")
	private String gatewayUrl;
	
	@Value("${tigererp.smsService}")
	private String smsService;
	
	private String phoneNumberWithCountryCode;
	private String message;
	
	@Autowired
	private SendSMSInfoService sendSMSInfoService;
	
	public TigerERPSMSSender() {
		
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGatewayUrl() {
		return gatewayUrl;
	}

	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}

	public String getPhoneNumberWithCountryCode() {
		return phoneNumberWithCountryCode;
	}

	public void setPhoneNumberWithCountryCode(String phoneNumberWithCountryCode) {
		this.phoneNumberWithCountryCode = phoneNumberWithCountryCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public String getSmsService() {
		return smsService;
	}

	public void setSmsService(String smsService) {
		this.smsService = smsService;
	}

	public boolean saveSMSInfo(String fullNumber, String smsBody, LedgerAccounts ledgerAccounts) {
		SendSMSInfo smsInfo = new SendSMSInfo();
		smsInfo.setSendNumber(fullNumber);
		smsInfo.setSmsBody(smsBody);
		smsInfo.setLedgerAccounts(ledgerAccounts);
		smsInfo = (SendSMSInfo)sendSMSInfoService.save(smsInfo);
		if(smsInfo.getId()> 0) {
			return true;
		}else {
			return false;
		}
	}

	public boolean SendSMS(String phoneNumberWithCountryCode, String message) throws Exception {
		if(smsService.equals(ConstantFactory.OFF)) {
			System.out.println("SMS Feature is turn off.");
			return false;
		}
		else {
			System.out.println("SMS feature is turn on.");
		}
		this.phoneNumberWithCountryCode = phoneNumberWithCountryCode;
		this.message = message;
		if(phoneNumberWithCountryCode != null) {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			
					MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
					RequestBody body = RequestBody.create(mediaType, "");
					
					//URL FOR GREENWEB
					//String msgURL = this.gatewayUrl+"?token="+this.userName+"&to="+this.phoneNumberWithCountryCode+"&message="+this.message;
					//URL FOR BULKSMSBD
					String msgURL = this.gatewayUrl+"?username="+this.userName+"&password="+this.password+"&number="+this.phoneNumberWithCountryCode+"&message="+this.message;
					System.out.println("SMS URL: "+msgURL);
					
					Request request = new Request.Builder()
					.url(msgURL)
					.method("POST", body)
					.addHeader("Content-Type", "application/x-www-form-urlencoded")
					.build();
					
					client.newCall(request).enqueue(new Callback() {
						
						@Override
						public void onResponse(Call call, Response response) throws IOException {
							// TODO Auto-generated method stub
							String smsResponse = response.body().toString();
							System.out.println(response);
							System.out.println("SMS SENT SUCCESSFULL");
						}
						
						@Override
						public void onFailure(Call call, IOException e) {
							// TODO Auto-generated method stub
							call.cancel();
						}
					});
		}
		else {
			System.out.println("------ INVALID PHONE NUMBER FOR SEND SMS --------------");
		}

		return true;
	}

}

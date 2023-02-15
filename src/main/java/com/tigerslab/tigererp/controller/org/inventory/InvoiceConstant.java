package com.tigerslab.tigererp.controller.org.inventory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontFactory;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tigerslab.tigererp.model.financial.LedgerEntries;
import com.tigerslab.tigererp.model.financial.Transaction;
import com.tigerslab.tigererp.model.inventory.Order;
import com.tigerslab.tigererp.model.org.Company;
import com.tigerslab.tigererp.repository.financial.LedgerAccountsRepository;
import com.tigerslab.tigererp.repository.financial.LedgerEntriesRepository;
import com.tigerslab.tigererp.service.financial.LedgerAccountsService;
import com.tigerslab.tigererp.service.financial.LedgerAccountsServiceImpl;
import com.tigerslab.tigererp.service.financial.LedgerEntriesServiceImpl;
import com.tigerslab.tigererp.service.org.CompanyProfileService;

@Component
public class InvoiceConstant {
	
	@Autowired
	LedgerEntriesRepository repo;
	@Autowired
	LedgerAccountsService service;
	
	@Autowired
	private CompanyProfileService companyProfileService;
	
	private int numOfDecimalPlaces = 0;
	private int counItem = 0;
	
	public void constantView(PDDocument doc, Float unit, Float dpi, Integer i, Float dataPerRowHeight, Float width, Float height, Float orderHeaderHeight, Float orderFooterHeight, Long id, PDPageContentStream contentStreams, Company company, Order order, Boolean chalan) throws IOException {
		
		File fontFile = new ClassPathResource("static\\font\\font.ttf").getFile();
		File fontIFile = new ClassPathResource("static\\font\\fontI.ttf").getFile();
		PDType0Font font = PDType0Font.load(doc, fontFile);
		PDType0Font fontI = PDType0Font.load(doc, fontIFile);
		contentStreams.beginText();
		contentStreams.newLineAtOffset(unit*2*dpi, (height-unit*3)*dpi);
		contentStreams.setFont(fontI, 16);
		contentStreams.setNonStrokingColor(24, 86, 161);
		contentStreams.showText("[ "+company.getCompanyName()+" ]");//############## COMPANY NAME ############## 
		contentStreams.endText();

		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-2)*dpi, (height-unit*4)*dpi);
		contentStreams.setFont(fontI, 32);
		if(!chalan) {
			contentStreams.showText("INVOICE");//############## INVOICE ############## 
		}else {
			contentStreams.showText("CHALAN");//############## CHALAN ############## 			
		}
		contentStreams.endText();

		contentStreams.beginText();
		contentStreams.newLineAtOffset(unit*2*dpi, (height-unit*5)*dpi);
		contentStreams.setFont(fontI, 10);
		contentStreams.setNonStrokingColor(0, 0, 0);
		contentStreams.setLeading(unit*2*dpi);
		contentStreams.showText("Propitor: "+company.getPropriotorName());//############## COMPANY ADDRESS ############## 
		contentStreams.newLine();
		contentStreams.showText("Address: " +company.getCompanyPhysicalAddress().iterator().next().getAddressLine1());//############## COMPANY ADDRESS ############## 
		contentStreams.newLine();
		contentStreams.showText("" +company.getCompanyPhysicalAddress().iterator().next().getAddressLine2());//############## COMPANY ADDRESS ############## 
		contentStreams.newLine();
		contentStreams.showText("City: "+company.getCompanyPhysicalAddress().iterator().next().getCity()+", ZIP: "+company.getCompanyPhysicalAddress().iterator().next().getZip()+", Mobile: +"+company.getCompanyPhysicalAddress().iterator().next().getAddressCountry().getPhoneCode()+" "
								+company.getCompanyPhysicalAddress().iterator().next().getMobilePhone());//############## COMPANY PHONE ############## 
		contentStreams.endText();

		contentStreams.setNonStrokingColor(24, 86, 161);
		contentStreams.addRect((width-3)*dpi, (height-1-unit)*dpi, (3-unit)*dpi, 22);
		contentStreams.fill();

		contentStreams.addRect((width-3)*dpi, (height-1-unit*6)*dpi, (3-unit)*dpi, 22);
		contentStreams.fill();

		contentStreams.addRect(unit*2*dpi, (height-1-unit*6)*dpi, (3-unit)*dpi, 22);
		contentStreams.fill();

		contentStreams.addRect(unit*2*dpi, (height-orderHeaderHeight+unit*3)*dpi, (width-unit*3)*dpi, 22);
		contentStreams.fill();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-3+unit*2)*dpi, (height-1)*dpi);
		contentStreams.setNonStrokingColor(255, 255, 255);
		contentStreams.showText("INVOICE #");
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-1-unit*2)*dpi, (height-1)*dpi);
		contentStreams.showText("DATE");
		contentStreams.endText();

		contentStreams.beginText();
		contentStreams.newLineAtOffset(unit*3*dpi, (height-1-unit*5)*dpi);
		contentStreams.showText("BILL TO");
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-3+unit*2)*dpi, (height-1-unit*5)*dpi);
		contentStreams.showText("ORDER ID");
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-1-unit*2)*dpi, (height-1-unit*5)*dpi);
		contentStreams.showText("CUSTOMER ID");
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset(unit*3*dpi, (height-orderHeaderHeight+unit*3)*dpi+7);
		contentStreams.setFont(font, 10);
		contentStreams.showText("DESCRIPTION");
		contentStreams.endText();

		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-3-unit*3)*dpi, (height-orderHeaderHeight+unit*3)*dpi+7);
		contentStreams.showText("QTY");
		contentStreams.endText();

		if(!chalan) {
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-3)*dpi, (height-orderHeaderHeight+unit*3)*dpi+7);
			contentStreams.showText("UNIT PRICE");
			contentStreams.endText();
	
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-2)*dpi, (height-orderHeaderHeight+unit*3)*dpi+7);
			contentStreams.showText("DISC (%)");
			contentStreams.endText();
	
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-1-unit*2)*dpi, (height-orderHeaderHeight+unit*3)*dpi+7);
			contentStreams.showText("AMOUNT");
			contentStreams.endText();
		}
		String tempOfflineInvoice = order.getOfflineInvoiceNumber();
		if(tempOfflineInvoice == null) {
			tempOfflineInvoice = "";
		}
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-3+unit*2)*dpi, (height-1-unit*2)*dpi-5);
		contentStreams.setNonStrokingColor(0, 0, 0);
		contentStreams.showText(""+tempOfflineInvoice);//############## ORDER INVOICE ############## 
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-1-unit*2)*dpi, (height-1-unit*2)*dpi-5);
		contentStreams.showText(""+ order.getOrderDate());//########### ORDER DATE ############## 
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-3+unit*2)*dpi, (height-1-unit*7)*dpi-5);
		contentStreams.showText("# "+id);//############## ORDER ID ############## 
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-1-unit*2)*dpi, (height-1-unit*7)*dpi-5);
		contentStreams.showText("# "+order.getParty().getId());//############## ORDER PARTY ID ##############
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset(unit*2*dpi, (height-1-unit*7)*dpi-5);
		contentStreams.setLeading(unit*2*dpi);
		contentStreams.showText("Name: "+order.getParty().getAccountName());//############## PARTY NAME ############## 
		//contentStreams.showText("Account Type: "+order.getParty().getParentAccount().getParentAccount().getName());//############## PARTY ACCOUNT TYPE ##############
		if(order.getParty().getAccountAddress().isEmpty()) {
			contentStreams.newLine();
			contentStreams.showText("Street Address: address refers to company.");//############## PARTY ADDRESS ############## 
			contentStreams.newLine();
			contentStreams.showText("address refers to company.");//############## PARTY ADDRESS line 2############## 
			contentStreams.newLine();
			contentStreams.showText("City: address refers to company., ZIP: address refers to company.");//############## PARTY ADDRESS ############## 
			contentStreams.newLine();
			contentStreams.showText("Mobile: address refers to company.");//############## PARTY PHONE ############## 
			//contentStreams.newLine();
			//contentStreams.showText("Email: "+order.getParty().getAccountAddress().iterator().next().getEmail());//############## PARTY EMAIL ############## 
			contentStreams.endText();
		}else {
			contentStreams.newLine();
			contentStreams.showText("Street Address: "+order.getParty().getAccountAddress().iterator().next().getAddressLine1());//############## PARTY ADDRESS ############## 
			contentStreams.newLine();
			contentStreams.showText(""+order.getParty().getAccountAddress().iterator().next().getAddressLine2());//############## PARTY ADDRESS line 2############## 
			contentStreams.newLine();
			contentStreams.showText("City: "+order.getParty().getAccountAddress().iterator().next().getCity()+", ZIP: "+
									order.getParty().getAccountAddress().iterator().next().getZip());//############## PARTY ADDRESS ############## 
			contentStreams.newLine();
			contentStreams.showText("Mobile: +"+order.getParty().getAccountAddress().iterator().next().getAddressCountry().getPhoneCode()+" "
									+order.getParty().getAccountAddress().iterator().next().getMobilePhone().getNumber());//############## PARTY PHONE ############## 
			//contentStreams.newLine();
			//contentStreams.showText("Email: "+order.getParty().getAccountAddress().iterator().next().getEmail());//############## PARTY EMAIL ############## 
			contentStreams.endText();
		}
		
		contentStreams.beginText();
		//contentStreams.newLineAtOffset((width/2-2)*dpi, orderFooterHeight*dpi);
		contentStreams.newLineAtOffset(unit*2*dpi, orderFooterHeight*dpi);
		contentStreams.showText("If you have any question about this Invoice, you may contact within 3 days.");
		contentStreams.endText();
		
		contentStreams.beginText();
		//contentStreams.newLineAtOffset((width/2-3)*dpi, (orderFooterHeight-unit*2)*dpi);
		contentStreams.newLineAtOffset(unit*2*dpi, (orderFooterHeight-unit*2)*dpi);
		contentStreams.setFont(fontI, 10);
		contentStreams.showText("[ Name: "+company.getCompanyPhysicalAddress().iterator().next().getContactPersonName()+", Email: "
								+company.getCompanyPhysicalAddress().iterator().next().getEmail()+", Mobile: +"
								+company.getCompanyPhysicalAddress().iterator().next().getAddressCountry().getPhoneCode()+" "
								+company.getCompanyPhysicalAddress().iterator().next().getMobilePhone()); //############## PARTY INQUERY ADDRESS ############## 
		contentStreams.setFont(font, 10);
		contentStreams.endText();

		contentStreams.beginText();
		//contentStreams.newLineAtOffset((width/2-2)*dpi, orderFooterHeight*dpi);
		contentStreams.newLineAtOffset((width-2+unit*5)*dpi, (orderFooterHeight-unit*2)*dpi);
		contentStreams.showText("Signature");
		contentStreams.endText();
		
		contentStreams.drawLine((width-2)*dpi, (orderFooterHeight-unit)*dpi, (width-unit)*dpi, (orderFooterHeight-unit)*dpi);
	}


	public void dataList(PDDocument doc, Float unit, Float dpi, Integer i, Float dataPerRowHeight, Float width, Float yTop, PDPageContentStream contentStreams, Integer stockItemIndex, Order order, Boolean chalan) throws IOException {

		File fontFile = new ClassPathResource("static\\font\\font.ttf").getFile();
		File fontIFile = new ClassPathResource("static\\font\\fontI.ttf").getFile();
		PDType0Font font = PDType0Font.load(doc, fontFile);
		PDType0Font fontI = PDType0Font.load(doc, fontIFile);
		
		Optional<Company> company = companyProfileService.findById((long)1);
		if(company.isPresent()) {
			this.numOfDecimalPlaces = company.get().getNumberOfDecimalPlaces();
		}
		else {
			this.numOfDecimalPlaces = 2;
		}
		counItem = counItem+1;
		contentStreams.drawLine(unit*2*dpi, yTop, (width-unit)*dpi, yTop);
		contentStreams.drawLine(unit*2*dpi, yTop, unit*2*dpi, yTop+(dataPerRowHeight*dpi));
		contentStreams.drawLine((width-unit)*dpi, yTop, (width-unit)*dpi, yTop+(dataPerRowHeight*dpi)); 
		if(!chalan) {
			contentStreams.drawLine((width-1-unit*3)*dpi-3, yTop, (width-1-unit*3)*dpi-3, yTop+(dataPerRowHeight*dpi));
			contentStreams.drawLine((width-2)*dpi-3, yTop, (width-2)*dpi-3, yTop+(dataPerRowHeight*dpi));
			contentStreams.drawLine((width-3)*dpi-3, yTop, (width-3)*dpi-3, yTop+(dataPerRowHeight*dpi));
		}
		contentStreams.drawLine((width-3-unit*4)*dpi-3, yTop, (width-3-unit*4)*dpi-3, yTop+(dataPerRowHeight*dpi));
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset(unit*3*dpi, yTop-(unit*2)*dpi+(dataPerRowHeight*dpi));
		if(order.getStockItems().get(stockItemIndex).getStockItem().getName().length()>((width*72)/(2*10)-20)) {
			contentStreams.showText(counItem+". "+order.getStockItems().get(stockItemIndex).getStockItem().getName()+"");//############## STOCK ITEM DESCRIPTION ############## .substring(0, (int)Math.floor((width*72)/(2*10)-20))
		}else {
			contentStreams.showText(counItem+". "+order.getStockItems().get(stockItemIndex).getStockItem().getName());//############## STOCK ITEM DESCRIPTION ##############
		}
		contentStreams.endText();
		Double stockQty = order.getStockItems().get(stockItemIndex).getQuantity();
		if(stockQty < 0) {
			stockQty = stockQty *-1;
		}
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-3-unit*4)*dpi, yTop-(unit*2)*dpi+(dataPerRowHeight*dpi));
		contentStreams.showText(""+ stockQty.toString());//############## STOCK ITEM QUANTITY ##############
		contentStreams.endText();

		if(!chalan) {
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-3)*dpi, yTop-(unit*2)*dpi+(dataPerRowHeight*dpi));
			contentStreams.showText(""+ order.getStockItems().get(stockItemIndex).getRate());//############## STOCK ITEM SELLING PRICE ##############
			contentStreams.endText();
	
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-2)*dpi, yTop-(unit*2)*dpi+(dataPerRowHeight*dpi));
			contentStreams.showText(""+ order.getStockItems().get(stockItemIndex).getDiscount());//############## STOCK ITEM DISCOUNT ##############
			contentStreams.endText();
		}
		BigDecimal r = order.getStockItems().get(stockItemIndex).getRate();
		Double q = order.getStockItems().get(stockItemIndex).getQuantity();
		BigDecimal d= order.getStockItems().get(stockItemIndex).getDiscount().divide(new BigDecimal(100));

		if(!chalan) {
			BigDecimal totalAmt = new BigDecimal(q).multiply(r).subtract(new BigDecimal(q).multiply(r).multiply(d)).setScale(numOfDecimalPlaces, BigDecimal.ROUND_HALF_EVEN);
			if(totalAmt.compareTo(new BigDecimal(0)) < 0) {
				totalAmt = totalAmt.multiply(new BigDecimal(-1));
			}
			
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-1-unit*2)*dpi, yTop-(unit*2)*dpi+(dataPerRowHeight*dpi));
			contentStreams.showText(""+ totalAmt);//############## STOCK ITEM TOTAL AMOUNT ##############
			contentStreams.endText();
		}
	}
	
	public void dataSummery(PDDocument doc, Float unit, Float dpi, Integer i, Float dataPerRowHeight, Float width, Float yTop, PDPageContentStream contentStreams, Order order, Boolean chalan) throws IOException {

		Double totalQuantity = (double) 0;
		BigDecimal totalRate = new BigDecimal(0);
		BigDecimal totalDisc = new BigDecimal(0);
		BigDecimal totalAmount= new BigDecimal(0);
		BigDecimal total= new BigDecimal(0);
		BigDecimal previousBalance = new BigDecimal(0);
		BigDecimal subTotal = new BigDecimal(0);
		BigDecimal grandTotal = new BigDecimal(0);
		BigDecimal paidAmount = order.getPaidAmount();
		BigDecimal dueAmount = new BigDecimal(0);
		
		if(!repo.findByledgerAccountsId(order.getParty().getId()).isEmpty()){
			List<LedgerEntries> ledgerEntries = repo.findByledgerAccountsId(order.getParty().getId());
			for(int l=0; l<ledgerEntries.size(); l++) {
				if(ledgerEntries.get(l).getOrder()!=null) {
					if(ledgerEntries.get(l).getOrder().getId()<order.getId()) {
						if(ledgerEntries.get(l).getTransactions()!=null) {
							previousBalance = previousBalance.add(ledgerEntries.get(l).getTransactions().getAmount());
						}
					}
				}else if(ledgerEntries.get(l).getVoucherType()==null && ledgerEntries.get(l).getOrder()==null) {
					previousBalance = previousBalance.add(ledgerEntries.get(l).getTransactions().getAmount());
				}
			}
		}
		
		for(int p = 0; p< order.getStockItems().size(); p++) {
			Double tempQuantity = order.getStockItems().get(p).getQuantity();
			if(tempQuantity < 0) {
				tempQuantity = tempQuantity *-1;
			}
			totalQuantity = totalQuantity+ tempQuantity;
			totalRate = totalRate.add(order.getStockItems().get(p).getRate());
			
			BigDecimal r = order.getStockItems().get(p).getRate();
			Double q = order.getStockItems().get(p).getQuantity();
			if(q < 0) {
				q = q*-1;
			}
			BigDecimal d= order.getStockItems().get(p).getDiscount().divide(new BigDecimal(100));
			
			totalDisc = totalDisc.add(new BigDecimal(q).multiply(r).multiply(d));
			totalAmount = totalAmount.add(new BigDecimal(q).multiply(r));//.subtract(totalDisc)
		}

		total=totalAmount.subtract(totalDisc);
		//previousBalance = previousBalance.subtract(total);
		subTotal = totalAmount.add(previousBalance);
		grandTotal = subTotal.subtract(totalDisc);
		dueAmount = grandTotal.subtract(paidAmount);
		
		//Rectangle
		contentStreams.setNonStrokingColor(210, 229, 252);
		contentStreams.addRect(unit*2*dpi, yTop-(dataPerRowHeight*dpi), (width-unit*3)*dpi, 25);
		contentStreams.fill();
		
		contentStreams.addRect((width-2)*dpi-4, yTop-(dataPerRowHeight*7*dpi), (2-unit)*dpi+4, dataPerRowHeight*7*dpi-4);
		contentStreams.fill();
		
		contentStreams.setNonStrokingColor(189, 219, 255);
		contentStreams.addRect((width-3-unit*4)*dpi-4, yTop-(dataPerRowHeight*7*dpi), (1+unit*4)*dpi, dataPerRowHeight*7*dpi-4);
		contentStreams.fill();
		//Rectangle
		
		//Text
		contentStreams.beginText();
		contentStreams.newLineAtOffset((unit*8)*dpi, yTop-(dataPerRowHeight*1*dpi)+unit*dpi);
		contentStreams.setNonStrokingColor(0,0,0);
		contentStreams.showText("Total");
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-3-unit*4)*dpi, yTop-(dataPerRowHeight*1*dpi)+unit*dpi);
		contentStreams.showText(""+ totalQuantity.toString());//############## STOCK ITEM TOTAL QUANTITY ##############
		contentStreams.endText();
		
		if(!chalan) {
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-3)*dpi, yTop-(dataPerRowHeight*1*dpi)+unit*dpi);
			contentStreams.showText("");//############## STOCK ITEM TOTAL UNIT PRICE ##############
			contentStreams.endText();
			
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-3-unit*4)*dpi, yTop-(dataPerRowHeight*2*dpi)+unit*dpi);
			contentStreams.showText("Previous Balance");
			contentStreams.endText();
	
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-3-unit*4)*dpi, yTop-(dataPerRowHeight*3*dpi)+unit*dpi);
			contentStreams.showText("Sub Total");
			contentStreams.endText();
	
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-3-unit*4)*dpi, yTop-(dataPerRowHeight*4*dpi)+unit*dpi);
			contentStreams.showText("Less Discount");
			contentStreams.endText();
	
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-3-unit*4)*dpi, yTop-(dataPerRowHeight*5*dpi)+unit*dpi);
			contentStreams.showText("Grand total");
			contentStreams.endText();
	
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-3-unit*4)*dpi, yTop-(dataPerRowHeight*6*dpi)+unit*dpi);
			contentStreams.showText("Paid Amount");
			contentStreams.endText();
	
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-3-unit*4)*dpi, yTop-(dataPerRowHeight*7*dpi)+unit*dpi);
			contentStreams.showText("Due Amount");
			contentStreams.endText();
			//Text
			
			//Value Text
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-2)*dpi, yTop-(dataPerRowHeight*1*dpi)+unit*dpi);
			contentStreams.showText(""+totalDisc.floatValue());//############## STOCK ITEM TOTAL DISCOUNT ##############
			contentStreams.endText();
	
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-1-unit*2)*dpi, yTop-(dataPerRowHeight*1*dpi)+unit*dpi);
			contentStreams.showText(""+totalAmount.setScale(numOfDecimalPlaces, BigDecimal.ROUND_HALF_EVEN));//############## STOCK ITEM TOTAL AMOUNT ##############
			contentStreams.endText();
			
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-2+unit)*dpi, yTop-(dataPerRowHeight*2*dpi)+unit*dpi);
			contentStreams.showText(""+ previousBalance.setScale(numOfDecimalPlaces, BigDecimal.ROUND_HALF_EVEN));//############## ORDER PARTY PREVIOUS BALANCE ##############
			contentStreams.endText();
	
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-2+unit)*dpi, yTop-(dataPerRowHeight*3*dpi)+unit*dpi);
			contentStreams.showText(""+subTotal.setScale(numOfDecimalPlaces, BigDecimal.ROUND_HALF_EVEN));//############## ORDER SUB TOTAL ##############
			contentStreams.endText();
	
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-2+unit)*dpi, yTop-(dataPerRowHeight*4*dpi)+unit*dpi);
			contentStreams.showText(""+totalDisc.setScale(numOfDecimalPlaces, BigDecimal.ROUND_HALF_EVEN));//############## ORDER TOTAL DISCOUNT ##############
			contentStreams.endText();
	
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-2+unit)*dpi, yTop-(dataPerRowHeight*5*dpi)+unit*dpi);
			contentStreams.showText(""+grandTotal.setScale(numOfDecimalPlaces, BigDecimal.ROUND_HALF_EVEN));//############## ORDER TOTAL DISCOUNT ##############
			contentStreams.endText();
	
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-2+unit)*dpi, yTop-(dataPerRowHeight*6*dpi)+unit*dpi);
			contentStreams.showText(""+paidAmount.setScale(numOfDecimalPlaces, BigDecimal.ROUND_HALF_EVEN));//############## ORDER PAID AMOUNT ##############
			contentStreams.endText();
	
			contentStreams.beginText();
			contentStreams.newLineAtOffset((width-2+unit)*dpi, yTop-(dataPerRowHeight*7*dpi)+unit*dpi);
			contentStreams.showText(""+dueAmount.setScale(numOfDecimalPlaces, BigDecimal.ROUND_HALF_EVEN));//############## ORDER DUE AMOUNT ##############
			contentStreams.endText();
			//Value Text
		}else {
			int spaceForLine = 6;
			String desc = "";
			if(order.getDescription().length()>(7*8*spaceForLine)) {
				desc = order.getDescription().substring(0,(7*8*6)-1);
			}else {
				desc = order.getDescription();
			}
			int charPerLine = 7*8;
			int totalLine = (int) Math.floor(desc.length()/charPerLine);
			if(desc.length()%charPerLine>0) {
				++totalLine;
			}
			for(int p=0; p<totalLine; p++) {
				if(p==totalLine-1) {
					contentStreams.beginText();
					contentStreams.newLineAtOffset((width-3-unit*4)*dpi, yTop-(dataPerRowHeight*(2+p)*dpi)+unit*dpi);
					
					if(desc.length()==order.getDescription().length()) {
						contentStreams.showText(desc.substring(0+p*charPerLine, desc.length()));
					}else {
						contentStreams.showText(desc.substring(0+p*charPerLine, (charPerLine)+p*charPerLine)+"...");
					}
					contentStreams.endText();
				}else {
					contentStreams.beginText();
					contentStreams.newLineAtOffset((width-3-unit*4)*dpi, yTop-(dataPerRowHeight*(2+p)*dpi)+unit*dpi);
					contentStreams.showText(desc.substring(0+p*charPerLine, (charPerLine)+p*charPerLine));
					contentStreams.endText();
				}
			}
		}
		
		//Horizontal
		contentStreams.drawLine(unit*2*dpi, yTop-(dataPerRowHeight*dpi), (width-unit)*dpi, yTop-(dataPerRowHeight*dpi));
		if(!chalan) {
			contentStreams.drawLine((width-3-unit*4)*dpi-3, yTop-(dataPerRowHeight*2*dpi), (width-unit)*dpi, yTop-(dataPerRowHeight*2*dpi));
			contentStreams.drawLine((width-3-unit*4)*dpi-3, yTop-(dataPerRowHeight*3*dpi), (width-unit)*dpi, yTop-(dataPerRowHeight*3*dpi));
			contentStreams.drawLine((width-3-unit*4)*dpi-3, yTop-(dataPerRowHeight*4*dpi), (width-unit)*dpi, yTop-(dataPerRowHeight*4*dpi));
			contentStreams.drawLine((width-3-unit*4)*dpi-3, yTop-(dataPerRowHeight*5*dpi), (width-unit)*dpi, yTop-(dataPerRowHeight*5*dpi));
			contentStreams.drawLine((width-3-unit*4)*dpi-3, yTop-(dataPerRowHeight*6*dpi), (width-unit)*dpi, yTop-(dataPerRowHeight*6*dpi));
		}
		contentStreams.drawLine((width-3-unit*4)*dpi-3, yTop-(dataPerRowHeight*7*dpi), (width-unit)*dpi, yTop-(dataPerRowHeight*7*dpi));
		//Horizontal
		
		//Vertical
		contentStreams.drawLine(unit*2*dpi, yTop, unit*2*dpi, yTop-(dataPerRowHeight*dpi));
		contentStreams.drawLine((width-unit)*dpi, yTop-(dataPerRowHeight*7*dpi), (width-unit)*dpi, yTop);
		if(!chalan) {
			contentStreams.drawLine((width-1-unit*3)*dpi-3, yTop, (width-1-unit*3)*dpi-3, yTop-(dataPerRowHeight*dpi));
			contentStreams.drawLine((width-3)*dpi-3, yTop, (width-3)*dpi-3, yTop-(dataPerRowHeight*dpi));
			contentStreams.drawLine((width-2)*dpi-3, yTop-(dataPerRowHeight*7*dpi), (width-2)*dpi-3, yTop);
		}
		contentStreams.drawLine((width-3-unit*4)*dpi-3, yTop-(dataPerRowHeight*7*dpi), (width-3-unit*4)*dpi-3, yTop);
		//Vertical
		this.counItem = 0;
	}
	
	public static ResponseEntity errorSetup(String message, Long id) throws IOException {
		
		Float unit = (float)1/8;
		Float dpi =72f;
		Integer i = 1;
		Float dataPerRowHeight = (float)3/8;
		Float width = 8f;
		Float height= 6f;
		Float orderHeaderHeight = 4f;
		Float orderFooterHeight = 0.5f;
		
		PDDocument doc = new PDDocument();
		PDRectangle size = new PDRectangle(width*72, height*72);
		PDPage page = new PDPage(size);
		PDPageContentStream contentStreams = new PDPageContentStream(doc, page);


		File fontFile = new ClassPathResource("static\\font\\font.ttf").getFile();
		File fontIFile = new ClassPathResource("static\\font\\fontI.ttf").getFile();
		PDType0Font font = PDType0Font.load(doc, fontFile);
		PDType0Font fontI = PDType0Font.load(doc, fontIFile);
		
		//#################################
		contentStreams.beginText();
		contentStreams.newLineAtOffset(unit*2*dpi, (height-unit*4)*dpi);
		contentStreams.setFont(fontI, 16);
		contentStreams.setNonStrokingColor(24, 86, 161);
		contentStreams.showText("[Company Name]");
		contentStreams.endText();

		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-2)*dpi, (height-unit*4)*dpi);
		contentStreams.setFont(fontI, 32);
		contentStreams.showText("INVOICE");
		contentStreams.endText();

		contentStreams.beginText();
		contentStreams.newLineAtOffset(unit*2*dpi, (height-unit*7)*dpi);
		contentStreams.setFont(fontI, 10);
		contentStreams.setNonStrokingColor(0, 0, 0);
		contentStreams.setLeading(unit*2*dpi);
		contentStreams.showText("[Street:------, Address:-------]");
		contentStreams.newLine();
		contentStreams.showText("[Phone: +8801xxxxxxxxx]");
		contentStreams.endText();

		contentStreams.setNonStrokingColor(24, 86, 161);
		contentStreams.addRect((width-3)*dpi, (height-1-unit)*dpi, (3-unit)*dpi, 22);
		contentStreams.fill();

		contentStreams.addRect((width-3)*dpi, (height-1-unit*6)*dpi, (3-unit)*dpi, 22);
		contentStreams.fill();

		contentStreams.addRect(unit*2*dpi, (height-1-unit*6)*dpi, (3-unit)*dpi, 22);
		contentStreams.fill();

		contentStreams.addRect(unit*2*dpi, (height-orderHeaderHeight+unit*3)*dpi, (width-unit*3)*dpi, 22);
		contentStreams.fill();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-3+unit*2)*dpi, (height-1)*dpi);
		contentStreams.setNonStrokingColor(255, 255, 255);
		contentStreams.showText("INVOICE #");
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-1-unit*2)*dpi, (height-1)*dpi);
		contentStreams.showText("DATE");
		contentStreams.endText();

		contentStreams.beginText();
		contentStreams.newLineAtOffset(unit*3*dpi, (height-1-unit*5)*dpi);
		contentStreams.showText("BILL TO");
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-3+unit*2)*dpi, (height-1-unit*5)*dpi);
		contentStreams.showText("ORDER ID");
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-1-unit*2)*dpi, (height-1-unit*5)*dpi);
		contentStreams.showText("CUSTOMER ID");
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset(unit*3*dpi, (height-orderHeaderHeight+unit*3)*dpi+7);
		contentStreams.setFont(font, 10);
		contentStreams.showText("DESCRIPTION");
		contentStreams.endText();

		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-3-unit*3)*dpi, (height-orderHeaderHeight+unit*3)*dpi+7);
		contentStreams.showText("QTY");
		contentStreams.endText();

		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-3)*dpi, (height-orderHeaderHeight+unit*3)*dpi+7);
		contentStreams.showText("UNIT PRICE");
		contentStreams.endText();

		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-2)*dpi, (height-orderHeaderHeight+unit*3)*dpi+7);
		contentStreams.showText("DISC (%)");
		contentStreams.endText();

		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-1-unit*2)*dpi, (height-orderHeaderHeight+unit*3)*dpi+7);
		contentStreams.showText("AMOUNT");
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-3+unit*2)*dpi, (height-1-unit*2)*dpi-5);
		contentStreams.setNonStrokingColor(0, 0, 0);
		contentStreams.showText("NULL");
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-1-unit*2)*dpi, (height-1-unit*2)*dpi-5);
		contentStreams.showText("NULL");
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-3+unit*2)*dpi, (height-1-unit*7)*dpi-5);
		contentStreams.showText("NULL # "+id);
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width-1-unit*2)*dpi, (height-1-unit*7)*dpi-5);
		contentStreams.showText("NULL");
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset(unit*2*dpi, (height-1-unit*7)*dpi-5);
		contentStreams.setLeading(unit*2*dpi);
		contentStreams.showText("[Name]");
		contentStreams.newLine();
		contentStreams.showText("[Company Name]");
		contentStreams.newLine();
		contentStreams.showText("[Street Address]");
		contentStreams.newLine();
		contentStreams.showText("[City, ST, ZIP]");
		contentStreams.newLine();
		contentStreams.showText("[Phone]");
		contentStreams.newLine();
		contentStreams.showText("[Email]");
		contentStreams.endText();
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((width/2-2)*dpi, orderFooterHeight*dpi);
		contentStreams.showText("If you have any question about this Invoice, you may contact within 3 days.");
		contentStreams.endText();

		contentStreams.beginText();
		contentStreams.newLineAtOffset((width/2-2-unit)*dpi, (orderFooterHeight-unit*2)*dpi);
		contentStreams.setFont(fontI, 10);
		contentStreams.showText("[Name:--------------------, Email:---------------------, Phone:----------]");
		contentStreams.setFont(font, 10);
		contentStreams.endText();
		//#################################
		
		contentStreams.beginText();
		contentStreams.newLineAtOffset((unit*3)*dpi, (height-orderHeaderHeight-unit)*dpi);
		contentStreams.setFont(fontI, 12);
		contentStreams.setNonStrokingColor(255, 82, 3);
		contentStreams.showText(message);
		contentStreams.endText();
		
		contentStreams.close();
		
		doc.addPage(page);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		doc.save(baos);
		doc.close();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "pdf"));
		headers.setContentLength(baos.toByteArray().length);

		return new ResponseEntity(baos.toByteArray(), headers, HttpStatus.CREATED);
	}
}

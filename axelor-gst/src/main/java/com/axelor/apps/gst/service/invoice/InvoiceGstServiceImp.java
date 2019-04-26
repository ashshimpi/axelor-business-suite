package com.axelor.apps.gst.service.invoice;

import com.axelor.apps.account.db.AccountManagement;
import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.account.db.TaxLine;
import com.axelor.apps.account.db.repo.InvoiceRepository;
import com.axelor.apps.account.service.app.AppAccountService;
import com.axelor.apps.account.service.invoice.InvoiceLineService;
import com.axelor.apps.account.service.invoice.factory.CancelFactory;
import com.axelor.apps.account.service.invoice.factory.ValidateFactory;
import com.axelor.apps.account.service.invoice.factory.VentilateFactory;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.service.PartnerService;
import com.axelor.apps.base.service.alarm.AlarmEngineService;
import com.axelor.apps.base.service.app.AppBaseService;
import com.axelor.apps.businessproject.service.InvoiceServiceProjectImpl;
import com.axelor.exception.AxelorException;
import com.google.inject.Inject;
import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class InvoiceGstServiceImp extends InvoiceServiceProjectImpl implements InvoiceGstService{

	@Inject
	public InvoiceGstServiceImp(
			ValidateFactory validateFactory,
			VentilateFactory ventilateFactory,
			CancelFactory cancelFactory,
			AlarmEngineService<Invoice> alarmEngineService,
			InvoiceRepository invoiceRepo,
			AppAccountService appAccountService,
			PartnerService partnerService,
			InvoiceLineService invoiceLineService) {
		super(validateFactory, ventilateFactory, cancelFactory, alarmEngineService, invoiceRepo, appAccountService,
				partnerService, invoiceLineService);
	}
	 
	@Override
	public Invoice compute(Invoice invoice) throws AxelorException {
		invoice = super.compute(invoice);
		BigDecimal netIgst=BigDecimal.ZERO;    	  
		BigDecimal netSgst=BigDecimal.ZERO;
		BigDecimal netCgst=BigDecimal.ZERO; 
		BigDecimal netAmount=BigDecimal.ZERO;
		BigDecimal grossAmount=BigDecimal.ZERO;
		BigDecimal netGstrate=BigDecimal.ZERO;
		BigDecimal taxTotal=BigDecimal.ZERO;
		BigDecimal inTaxtotal=BigDecimal.ZERO;
		
		List<InvoiceLine> invoiceLines = invoice.getInvoiceLineList();
		 if(invoiceLines==null || invoiceLines.isEmpty()) {
			 return invoice;
		 }
		 else {
			 for(InvoiceLine invoiceLine: invoiceLines){
			
				netAmount=netAmount.add(invoiceLine.getExTaxTotal());
				netIgst=netIgst.add(invoiceLine.getIgst());
				netSgst=netSgst.add(invoiceLine.getSgst());
				netCgst=netCgst.add(invoiceLine.getCgst());
				BigDecimal temp=invoiceLine.getGrossAmount();
				grossAmount=grossAmount.add(temp);
				netGstrate=netGstrate.add(invoiceLine.getGstRate());
				
				taxTotal=invoice.getTaxTotal().add(grossAmount).setScale(AppBaseService.DEFAULT_NB_DECIMAL_DIGITS,RoundingMode.HALF_EVEN);
				
				System.err.println(""+taxTotal);
				inTaxtotal=invoice.getExTaxTotal().add(taxTotal).setScale(AppBaseService.DEFAULT_NB_DECIMAL_DIGITS,RoundingMode.HALF_EVEN);
				System.err.println(""+inTaxtotal);
			}
			invoice.setNetAmount(netAmount);
			invoice.setNetIgst(netIgst);
			invoice.setNetSgst(netSgst);
			invoice.setNetCgst(netCgst);
			invoice.setGrossAmount(grossAmount);
			invoice.setNetGstrate(netGstrate);
			invoice.setTaxTotal(taxTotal);
			invoice.setInTaxTotal(inTaxtotal);
			
			return invoice;
		 }
		 
	}

	@Override
	public InvoiceLine setNewInvoice(InvoiceLine invoiceLines) {
		Product product=invoiceLines.getProduct();
		
		if(product.getGstRate()!=null)
		{
			invoiceLines.setGstRate(product.getGstRate());
			System.err.println(""+product.getGstRate());
			System.err.println(""+invoiceLines.getGstRate());
		}
		if(product.getName()!=null)
		{
			invoiceLines.setProductName(product.getName());
		}
		
		if(product.getSalePrice()!=null)
		{
			invoiceLines.setPrice(product.getSalePrice());
			System.err.println(""+product.getSalePrice());
		}
		if(invoiceLines.getQty()!=null)
		{
			invoiceLines.setQty(new BigDecimal("1"));
			System.err.println(""+invoiceLines.getQty());
		}
		invoiceLines.setExTaxTotal(invoiceLines.getPrice().multiply(invoiceLines.getQty()));
		
		
		System.err.println(invoiceLines);
		return invoiceLines;
	}

}

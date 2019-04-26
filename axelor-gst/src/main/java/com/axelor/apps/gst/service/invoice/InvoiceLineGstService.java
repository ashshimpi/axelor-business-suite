package com.axelor.apps.gst.service.invoice;

import java.math.BigDecimal;

import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.gst.db.State;


public class InvoiceLineGstService implements InvoiceLineService{

	@Override
	public InvoiceLine calculateInvoiceLine(InvoiceLine invoiceLine, State invoiceState, State companyState, BigDecimal TotalWT) {
		BigDecimal igst = BigDecimal.ZERO;
		BigDecimal sgst = BigDecimal.ZERO;
		BigDecimal cgst = BigDecimal.ZERO;
		BigDecimal grossAmount = BigDecimal.ZERO;
		TotalWT = invoiceLine.getPrice().multiply(invoiceLine.getQty());
		// (new BigDecimal(invoiceLine.getQty()));
		if (!(invoiceState.equals(companyState))) {
			igst = TotalWT.multiply(invoiceLine.getGstRate());
		} else {
			sgst = TotalWT.multiply(invoiceLine.getGstRate().divide(new BigDecimal(200)));
			cgst = TotalWT.multiply(invoiceLine.getGstRate().divide(new BigDecimal(200)));
		}
		grossAmount = grossAmount.add(igst).add(sgst).add(cgst);
		invoiceLine.setIgst(igst);
		invoiceLine.setSgst(sgst);
		invoiceLine.setCgst(cgst);
		invoiceLine.setGrossAmount(grossAmount);

		return invoiceLine;
	}
	
	
}

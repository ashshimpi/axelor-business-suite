package com.axelor.apps.gst.service.invoice;

import java.math.BigDecimal;

import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.gst.db.State;

public interface InvoiceLineService {
	public InvoiceLine calculateInvoiceLine(
		      InvoiceLine invoiceLine, State invoiceState, State companyState, BigDecimal TotalWT);
}

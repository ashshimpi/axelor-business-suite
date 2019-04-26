package com.axelor.apps.gst.service.invoice;

import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.account.service.invoice.InvoiceService;

public interface InvoiceGstService extends InvoiceService{
	
	public InvoiceLine setNewInvoice(InvoiceLine invoiceLines);
}

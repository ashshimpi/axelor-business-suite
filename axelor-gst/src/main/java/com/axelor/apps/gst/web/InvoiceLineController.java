package com.axelor.apps.gst.web;

import java.math.BigDecimal;
import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.gst.db.State;
import com.axelor.apps.gst.service.invoice.InvoiceLineService;
import com.google.inject.Inject;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class InvoiceLineController {

	@Inject
	public InvoiceLineService invoiceLineService;
	
	public void calculateInvoiceLine(ActionRequest request, ActionResponse response) {
		Invoice invoice = request.getContext().getParent().asType(Invoice.class);
		InvoiceLine invoiceLine = request.getContext().asType(InvoiceLine.class);
		State invoiceAddresss = invoice.getAddress().getState();
		State companyAddress = invoice.getCompany().getAddress().getState();
		if (invoiceAddresss != null && companyAddress != null) {
			BigDecimal TotalWT = invoice.getExTaxTotal();
			System.err.println("" + invoiceAddresss);
			System.err.println("" + companyAddress);
			System.err.println("" + TotalWT);
			invoiceLine = invoiceLineService.calculateInvoiceLine(invoiceLine, invoiceAddresss, companyAddress,
					TotalWT);
			response.setValue("igst", invoiceLine.getIgst());
			response.setValue("sgst", invoiceLine.getSgst());
			response.setValue("cgst", invoiceLine.getCgst());
			response.setValue("grossAmount", invoiceLine.getGrossAmount());
		}
		else {
			response.setError("State is empty");
		}
	
	}
}

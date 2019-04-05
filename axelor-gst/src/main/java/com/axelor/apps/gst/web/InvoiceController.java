package com.axelor.apps.gst.web;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.gst.db.State;
import com.axelor.apps.gst.service.invoice.InvoiceService;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;
import java.math.BigDecimal;

public class InvoiceController extends com.axelor.apps.account.web.InvoiceController {

  @Inject private InvoiceService invoiceService;

  // public void compute(ActionRequest request, ActionResponse response) throws AxelorException {

  @Override
  public void compute(ActionRequest request, ActionResponse response) {
    Invoice invoice = request.getContext().getParent().asType(Invoice.class);
    InvoiceLine invoiceLine = request.getContext().asType(InvoiceLine.class);

    State invoiceAddresss = invoice.getAddress().getState();
    State companyAddress = invoice.getCompany().getAddress().getState();
    BigDecimal TotalWT = invoiceLine.getExTaxTotal();

    System.err.println("" + invoiceAddresss);
    System.err.println("" + companyAddress);
    System.err.println("" + TotalWT);

    invoiceLine =
        invoiceService.calculateInvoiceLine(invoiceLine, invoiceAddresss, companyAddress, TotalWT);
    response.setValue("igst", invoiceLine.getIgst());
    response.setValue("sgst", invoiceLine.getSgst());
    response.setValue("cgst", invoiceLine.getCgst());
    response.setValue("grossAmount", invoiceLine.getGrossAmount());

    super.compute(request, response);
  }
}

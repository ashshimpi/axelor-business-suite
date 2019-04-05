package com.axelor.apps.gst.service.invoice;

import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.gst.db.State;
import java.math.BigDecimal;

public interface InvoiceService {
  public InvoiceLine calculateInvoiceLine(
      InvoiceLine invoiceLine, State invoiceState, State companyState, BigDecimal TotalWT);
}

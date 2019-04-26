package com.axelor.apps.gst;

import com.axelor.app.AxelorModule;
import com.axelor.apps.account.service.invoice.print.InvoicePrintServiceImpl;
import com.axelor.apps.businessproject.service.InvoiceServiceProjectImpl;
import com.axelor.apps.gst.service.invoice.*;
import com.axelor.apps.gst.service.invoice.print.InvoiceGstPrintServiceImpl;

public class GstModule extends AxelorModule {

  @Override
  protected void configure() {
    bind(InvoiceLineService.class).to(InvoiceLineGstService.class);
    bind(InvoiceServiceProjectImpl.class).to(InvoiceGstServiceImp.class);
    bind(InvoiceGstService.class).to(InvoiceGstServiceImp.class);
    bind(InvoicePrintServiceImpl.class).to(InvoiceGstPrintServiceImpl.class);
  }
}

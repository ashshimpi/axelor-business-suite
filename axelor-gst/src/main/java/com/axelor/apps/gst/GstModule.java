package com.axelor.apps.gst;

import com.axelor.app.AxelorModule;
import com.axelor.apps.gst.service.invoice.*;

public class GstModule extends AxelorModule {

  @Override
  protected void configure() {
    bind(InvoiceService.class).to(InvoiceServiceImpl.class);
  }
}

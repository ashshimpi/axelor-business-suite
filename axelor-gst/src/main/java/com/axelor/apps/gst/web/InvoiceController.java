package com.axelor.apps.gst.web;

import java.util.ArrayList;
import java.util.List;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.repo.ProductRepository;
import com.axelor.apps.gst.service.invoice.InvoiceGstService;
import com.google.inject.Inject;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class InvoiceController {
	@Inject
	ProductRepository productRepository;
	@Inject
	InvoiceGstService invoiceService;
	
	public void selectedProduct(ActionRequest request, ActionResponse response) {
		// Invoice invoice = request.getContext().asType(Invoice.class);
		
		@SuppressWarnings("unchecked")
		List<Integer> productId = (List<Integer>) request.getContext().get("_ids");
			
			try {
				System.err.println(productId);
				List<InvoiceLine> invoiceLines = new ArrayList<InvoiceLine>();
				for (Integer product : productId) {
					Product product2 = productRepository.find(product.longValue());
					System.err.println(product2);

					InvoiceLine invoiceLine = new InvoiceLine();
					invoiceLine.setProduct(product2);
					try {
						invoiceLine = invoiceService.setNewInvoice(invoiceLine);
						System.err.println(invoiceLine);
						invoiceLines.add(invoiceLine);
					} catch (Exception e) {
						System.err.println(e);
					}

				}
				response.setValue("invoiceLineList", invoiceLines);

			} catch (Exception e) {
				System.err.println("no product selected");
			}
		
	}

}

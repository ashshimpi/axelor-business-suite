package com.axelor.apps.gst.web;

import com.axelor.apps.ReportFactory;
import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.account.db.repo.InvoiceRepository;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.repo.ProductBaseRepository;
import com.axelor.apps.base.service.app.AppBaseService;
import com.axelor.apps.base.service.user.UserService;
import com.axelor.apps.gst.report.IReport;
import com.axelor.apps.report.engine.ReportSettings;
import com.axelor.auth.db.User;
import com.axelor.exception.AxelorException;
import com.axelor.i18n.I18n;
import com.axelor.inject.Beans;
import com.axelor.meta.schema.actions.ActionView;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.common.base.Joiner;

import java.lang.invoke.MethodHandles;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GSTProductController {
	
	  private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public void printGstProductCatalog(ActionRequest request, ActionResponse response)
		      throws AxelorException {

		    User user = Beans.get(UserService.class).getUser();

		    int currentYear = Beans.get(AppBaseService.class).getTodayDateTime().getYear();
		    String productIds = "";

		    List<Integer> lstSelectedProduct = (List<Integer>) request.getContext().get("_ids");

		    if (lstSelectedProduct != null) {
		      productIds = Joiner.on(",").join(lstSelectedProduct);
		    }

		    String name = I18n.get("Product Catalog");

		    String fileLink =
		        ReportFactory.createReport(IReport.PRODUCT_CATALOG, name + "-${date}")
		            .addParam("UserId", user.getId())
		            .addParam("CurrYear", Integer.toString(currentYear))
		            .addParam("ProductIds", productIds)
		            .addParam("Locale", ReportSettings.getPrintingLocale(null))
		            .generate()
		            .getFileLink();

		    logger.debug("Printing " + name);

		    response.setView(ActionView.define(name).add("html", fileLink).map());
		  }
}

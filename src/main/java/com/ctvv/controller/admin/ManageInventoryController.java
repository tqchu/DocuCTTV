package com.ctvv.controller.admin;

import com.ctvv.model.*;
import com.ctvv.service.InventoryServices;
import com.ctvv.util.JasperReportUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ManageInventoryController", value = "/admin/inventory/*")
public class ManageInventoryController
		extends HttpServlet {

	private final InventoryServices inventoryServices = new InventoryServices();

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// VIEW HISTORY DETAIL
		if (request.getRequestURI().equals(request.getContextPath() + "/admin/inventory/history/view")) {
			inventoryServices.viewHistoryDetail(request, response);
		}
		// INVENTORY HISTORY HOME

		else if (request.getRequestURI().startsWith(request.getContextPath() + "/admin/inventory/history")) {
			inventoryServices.listImport(request, response);
		}
		// INVENTORY HOME
		else {
			String action = request.getParameter("action");
			if (action != null) {
				switch (action) {
					case "create":
						inventoryServices.showAddNewImportForm(request, response);
						break;
				}
			} else {
				inventoryServices.listStockItems(request, response);
			}
		}
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if ((path!=null) && (path.equals("/history/download"))) {
			inventoryServices.downloadImportReport(request, response);
		} else {
			String action = request.getParameter("action");
			switch (action) {
				case "create":
					inventoryServices.create(request, response);
					break;

			}
		}
	}


}

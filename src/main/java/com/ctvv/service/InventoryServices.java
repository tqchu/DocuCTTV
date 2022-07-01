package com.ctvv.service;

import com.ctvv.dao.*;
import com.ctvv.model.*;
import com.ctvv.util.JasperReportUtils;
import com.ctvv.util.RequestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InventoryServices {
	final String MANAGE_HOME_PAGE = "/admin/manage/home.jsp";
	final String INVENTORY_SERVLET = "/admin/inventory";
	final String HISTORY_SERVLET = "/admin/inventory/history";
	final int NUMBER_OF_RECORDS_PER_MANAGE_INVENTORY_PAGE = 10;
	private HttpSession httpSession;
	private final ImportDAO importDAO = new ImportDAO();
	private final ProductDAO productDAO = new ProductDAO();
	private final ProviderDAO providerDAO = new ProviderDAO();
	private final ImportDetailDAO importDetailDAO = new ImportDetailDAO();
	private final StockItemDAO stockItemDAO = new StockItemDAO();


	public void viewHistoryDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Import anImport = importDAO.get(id);
		request.setAttribute("anImport", anImport);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/inventory/historyDetail.jsp");
		dispatcher.forward(request, response);
	}

	public void listImport(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                        IOException {
		String keyword = request.getParameter("keyword");
		LocalDateTime from = request.getParameter("from") != null ?
				LocalDate.parse(request.getParameter("from")).atStartOfDay()
				: null;
		LocalDateTime to = request.getParameter("to") != null ?
				LocalDate.parse(request.getParameter("to")).atTime(23, 59, 59) : null;
		String orderBy = getOrder(request);
		List<Import> importList;
		int begin = RequestUtils.getBegin(request,NUMBER_OF_RECORDS_PER_MANAGE_INVENTORY_PAGE );
		importList = importDAO.get(begin, NUMBER_OF_RECORDS_PER_MANAGE_INVENTORY_PAGE, keyword, from, to, orderBy, null);
		int numberOfPages = (importDAO.count(keyword, from, to) - 1) / NUMBER_OF_RECORDS_PER_MANAGE_INVENTORY_PAGE + 1;
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("importList", importList);
		goHistoryHome(request, response);
	}


	private String getOrder(HttpServletRequest request) {
		String orderBy = request.getParameter("orderBy");
		if (orderBy != null) {
			switch (orderBy) {
				case "default":
					orderBy = null;
					break;
				case "name":
					orderBy = "provider_name";
					break;
			}
		}
		return orderBy;
	}

	public void showAddNewImportForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Product> productList = productDAO.getAll("product_name", "ASC");
		List<Provider> providerList = providerDAO.getAll("provider_name", "ASC");
		request.setAttribute("providerList", providerList);
		request.setAttribute("productList", productList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/inventory/addForm" +
				".jsp");
		dispatcher.forward(request, response);
	}

	public void downloadImportReport(HttpServletRequest request, HttpServletResponse response) {
		// gets MIME type of the file
		String mimeType = "application/pdf";

		// modifies response
		response.setContentType(mimeType);

		// forces download
		String headerKey = "Content-Disposition";
		String headerValue = null;
		try {
			headerValue = String.format("attachment; filename=\"%s\"", URLEncoder.encode("Chi-tiết-đơn-nhập.pdf",
					"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setHeader(headerKey, headerValue);

		// obtains response's output stream

		int id = Integer.parseInt(request.getParameter("id"));
		Import anImport = importDAO.get(id);
		byte[] bytes = JasperReportUtils.createImportReport(anImport);
		response.setContentLength(bytes.length);
		OutputStream os;
		try {
			os = response.getOutputStream();
			os.write(bytes);
			os.flush();
			os.close();


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {

		httpSession = request.getSession();
		int providerId = Integer.parseInt(request.getParameter("providerId"));
		Provider provider = providerDAO.get(providerId);
		String providerTaxId = provider.getTaxId();
		String importerName = request.getParameter("importerName");

		LocalDateTime importDate = LocalDateTime.now();
		String[] productIdParams = request.getParameterValues("productId");
		String[] quantityParams = request.getParameterValues("quantity");
		String[] priceParams = request.getParameterValues("price");
		String[] taxParams = request.getParameterValues("tax");

		List<ImportDetail> importDetailList = new ArrayList<>();
		int numberOfProducts = productIdParams.length;
		for (int i = 0; i < numberOfProducts; i++) {
			int productId = Integer.parseInt(productIdParams[i]);
			Product product = productDAO.get(productId);
			int quantity = Integer.parseInt(quantityParams[i]);
			int price = Integer.parseInt(priceParams[i]);
			double tax = Integer.parseInt(taxParams[i]) / 100.0;
			importDetailList.add(new ImportDetail(productId, product.getName(), quantity, price, tax));
		}
		Import anImport = new Import(importerName, providerId,  provider.getProviderName(),providerTaxId, importDate
				, 0,
				importDetailList);
		importDAO.create(anImport);

		httpSession.setAttribute("successMessage", "Thêm đơn thành công");
		response.sendRedirect(request.getContextPath() + HISTORY_SERVLET);
	}

	public void listStockItems(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                            IOException {
		String keyword = request.getParameter("keyword");
		String sortBy = request.getParameter("sortBy");
		if (sortBy == null || sortBy.equals("name")) {
			sortBy = "product_name";
		}
		int begin = RequestUtils.getBegin(request, NUMBER_OF_RECORDS_PER_MANAGE_INVENTORY_PAGE);
		int numberOfPage = (productDAO.count(keyword, "product_name") - 1) / NUMBER_OF_RECORDS_PER_MANAGE_INVENTORY_PAGE + 1;
		List<StockItem> stockItemList = stockItemDAO.get(begin, NUMBER_OF_RECORDS_PER_MANAGE_INVENTORY_PAGE, keyword, sortBy, "ASC");
		request.setAttribute("list", stockItemList);
		request.setAttribute("numberOfPages", numberOfPage);
		goInventoryHome(request, response);
	}

	private void goHistoryHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                            IOException {
		request.setAttribute("tab", "inventoryHistory");
		RequestDispatcher dispatcher = request.getRequestDispatcher(MANAGE_HOME_PAGE);
		dispatcher.forward(request, response);
	}

	private void goInventoryHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                              IOException {
		request.setAttribute("tab", "inventory");
		RequestDispatcher dispatcher = request.getRequestDispatcher(MANAGE_HOME_PAGE);
		dispatcher.forward(request, response);
	}
}

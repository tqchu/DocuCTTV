package com.ctvv.controller.admin;

import com.ctvv.dao.ImportDAO;
import com.ctvv.dao.ImportDetailDAO;
import com.ctvv.dao.ProductDAO;
import com.ctvv.dao.ProviderDAO;
import com.ctvv.model.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ManageInventoryController", value = "/admin/inventory/*")
public class ManageInventoryController
		extends HttpServlet {
	final int NUMBER_OF_RECORDS_PER_PAGE = 5;
	final String HOME_PAGE = "/admin/manage/home.jsp";
	final String INVENTORY_SERVLET = "/admin/inventory";
	final String HISTORY_SERVLET = "/admin/inventory/history";
	private HttpSession httpSession;
	private ImportDAO importDAO;
	private ProductDAO productDAO;
	private ProviderDAO providerDAO;
	private ImportDetailDAO importDetailDAO;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		httpSession = request.getSession();
		// INVENTORY HISTORY HOME
		if (request.getRequestURI().equals(request.getContextPath() + "/admin/inventory/history")) {
			listImport(request, response);
		}
		// VIEW HISTORY DETAIL
		else if (request.getRequestURI().equals(request.getContextPath() + "/admin/inventory/history/view")) {
			viewHistoryDetail(request, response);
		}
		// INVENTORY HOME
		else {
			String action = request.getParameter("action");
			if (action != null) {
				switch (action) {
					case "create":
						List<Product> productList = productDAO.getAll();
						List<Provider> providerList = providerDAO.getAll();
						request.setAttribute("providerList", providerList);
						request.setAttribute("productList", productList);
						RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/inventory/addForm" +
								".jsp");
						dispatcher.forward(request, response);
						break;
				}

			}
			// Liệt kê danh sách tồn kho
			else {
				goInventoryHome(request, response);
			}
		}
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		httpSession = request.getSession();
		String action = request.getParameter("action");
		switch (action) {
			case "create":
				create(request, response);
				break;

		}
	}

	private void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int providerId = Integer.parseInt(request.getParameter("providerId"));
		Provider provider = providerDAO.get(providerId);
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
		Import anImport = new Import(importerName, providerId, provider.getProviderName(), importDate, 0,
				importDetailList);
		importDAO.create(anImport);

		httpSession.setAttribute("successMessage", "Thêm đơn thành công");
		response.sendRedirect(request.getContextPath() + INVENTORY_SERVLET);

	}

	private void listImport(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		String orderBy = getOrder(request);
		List<Import> importList;
		int begin = getBegin(request);
		importList = importDAO.get(begin, NUMBER_OF_RECORDS_PER_PAGE, keyword, orderBy, null);
		int numberOfPages = (importDAO.count(keyword, null) - 1) / NUMBER_OF_RECORDS_PER_PAGE + 1;
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("importList", importList);
		goHistoryHome(request, response);
	}

	private void viewHistoryDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                                IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Import anImport = importDAO.get(id);
		request.setAttribute("anImport", anImport);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/inventory/historyDetail.jsp");
		dispatcher.forward(request, response);
	}

	private void goInventoryHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                              IOException {
		request.setAttribute("tab", "inventory");
		RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_PAGE);
		dispatcher.forward(request, response);
	}

	public String getOrder(HttpServletRequest request) {
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

	public int getBegin(HttpServletRequest request) {
		String pageParam = request.getParameter("page");
		int page;
		if (pageParam == null) {
			page = 1;
		} else {
			page = Integer.parseInt(pageParam);
		}
		return NUMBER_OF_RECORDS_PER_PAGE * (page - 1);
	}

	private void goHistoryHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                            IOException {
		request.setAttribute("tab", "inventoryHistory");
		RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_PAGE);
		dispatcher.forward(request, response);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			importDAO = new ImportDAO(dataSource);
			productDAO = new ProductDAO(dataSource);
			providerDAO = new ProviderDAO(dataSource);
			importDetailDAO = new ImportDetailDAO(dataSource);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}

package com.ctvv.controller.admin;

import com.ctvv.dao.ProductDAO;
import com.ctvv.dao.ProviderDAO;
import com.ctvv.model.Provider;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManageProviderController", value = "/admin/providers/*")
public class ManageProviderController
		extends HttpServlet {
	private final String HOME_PAGE = "/admin/manage/home.jsp";
	private final String SEARCH_SERVLET = "/admin/providers/search";
	private ProviderDAO providerDAO;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.equals(request.getContextPath() + SEARCH_SERVLET)) {
			search(request, response);
		} else
			listProviders(request, response);
	}

	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String field = request.getParameter("field");
		String keyword = request.getParameter("keyword");
		switch (field){
			case "name":
				field= "provider_name";
				break;
			case "address":
				field = "provider_address";
				break;
			case "phoneNumber":
				field="phone_number";
				break;
			case "taxId":
				field="tax_id_number";
				break;
		}
		List<Provider> providerList = providerDAO.search(keyword, field);
		request.setAttribute("providerList", providerList);
		goHome(request, response);
	}

	private void listProviders(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Provider> providerList = providerDAO.getAll();
		request.setAttribute("providerList", providerList);
		goHome(request, response);
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		request.setAttribute("tab", "providers");
		RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_PAGE);
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
			case "create":
				create(request, response);
				break;
			case "update":
				update(request, response);
				break;
			case "delete":
				delete(request, response);
				break;
		}
	}

	private void create(HttpServletRequest request, HttpServletResponse response) {
		// Nhận các parameter có name là "name", "address", "phoneNumber", "email", "taxId"

		// Phát hiện trùng tên, số điện thoại, email, mã số thuế
		// (bằng cách sử dụng các hàm findByName, findByPhoneNumber, findByEmail, findByTaxId)


		// set các attribute trong session: nameErrorMessage, phoneNumberMessage ... nếu phát hiện trùng lặp tên, sdt,
		// sendRedirect về trang chính + /admin/providers

		// goi ham create

		// set attribute successMessage trong session nếu thành công
		// sendRedirect về trang chính + /admin/providers
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
		// Nhận các parameter có name là "id", "name", "address", "phoneNumber", "email", "taxId"

		// Phát hiện trùng tên, số điện thoại, email, mã số thuế
		// (bằng cách sử dụng các hàm findByName, findByPhoneNumber, findByEmail, findByTaxId)


		// set các attribute trong session: nameErrorMessage, phoneNumberMessage ... nếu phát hiện trùng lặp tên, sdt,
		// sendRedirect về trang chính + /admin/providers

		// goi ham update

		// set attribute successMessage trong session nếu thành công
		// sendRedirect về trang chính + /admin/providers


	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		// Nhan parameter có name là "id"
		// goi ham delete
		// set successMessage trong session
		// sendRedirect về trang chính + /admin/providers

	}

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			providerDAO = new ProviderDAO(dataSource);
		} catch (NamingException e) {
			throw new ServletException();
		}
	}
}

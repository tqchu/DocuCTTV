package com.ctvv.controller.admin;

import com.ctvv.dao.ProviderDAO;
import com.ctvv.model.Provider;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManageProviderController", value = "/admin/providers/*")
public class ManageProviderController
		extends HttpServlet {
	private final String HOME_PAGE = "/admin/manage/home.jsp";
	private final String SEARCH_SERVLET = "/admin/providers/search";
	private ProviderDAO providerDAO;
	private HttpSession session;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.equals(request.getContextPath() + SEARCH_SERVLET)) {
			search(request, response);
		} else
			listProviders(request, response);
	}

	private void search(HttpServletRequest request, HttpServletResponse response) {

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
		int providerId = Integer.parseInt(request.getParameter("provider_id"));
		String providerName = request.getParameter("provider_name");
		String address = request.getParameter("address");
		String phoneNumber = request.getParameter("phoneNumber");
		String email = request.getParameter("email");
		String taxId = request.getParameter("taxId");
		session=request.getSession();

		// Phát hiện trùng tên, số điện thoại, email, mã số thuế
		// (bằng cách sử dụng các hàm findByName, findByPhoneNumber, findByEmail, findByTaxId)
		if (providerDAO.findByEmail(email) == null)
		{
			Provider provider = new Provider(providerId,providerName,address,phoneNumber,email,taxId);
			providerDAO.update(provider);
			session.setAttribute("successMessage","Cập nhật thành công!");
			try {
				response.sendRedirect(request.getContextPath() + "/admin/providers");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			session.setAttribute("errorMessage","Email đã tồn tại!");
			try {
				response.sendRedirect(request.getContextPath() + "/admin/providers");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (providerDAO.findByTaxId(taxId) == null)
		{
			Provider provider = new Provider(providerId,providerName,address,phoneNumber,email,taxId);
			providerDAO.update(provider);
			session.setAttribute("successMessage","Cập nhật thành công");
			try {
				response.sendRedirect(request.getContextPath() + "/admin/providers");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		else
		{
			session.setAttribute("taxIdErrorMessage","Mã số thuế đã tồn tại!");
			try {
				response.sendRedirect(request.getContextPath() + "/admin/providers");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

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

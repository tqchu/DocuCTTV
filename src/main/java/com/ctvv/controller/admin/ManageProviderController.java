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
import java.util.Objects;

@WebServlet(name = "ManageProviderController", value = "/admin/providers/*")
public class ManageProviderController
		extends HttpServlet {
	private ProviderDAO providerDAO;
    final String HOME_PAGE = "/admin/manage/home.jsp";
	final String SEARCH_SERVLET ="/admin/providers/search";
	private HttpSession session;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String uri = request.getRequestURI();
		if (uri.equals(request.getContextPath() + SEARCH_SERVLET)) {
			search(request, response);
		} else
			listProviders(request, response);
	}

	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		String field = request.getParameter("field");
		String keyword = request.getParameter("keyword");
		switch (field) {
			case "name":
				field = "provider_name";
				break;
			case "address":
				field = "provider_address";
				break;
			case "phoneNumber":
				field = "phone_number";
				break;
			case "taxId":
				field = "tax_id_number";
				break;
		}
		String orderBy= getOrder(request);
		List<Provider> providerList;
		providerList = providerDAO.search(keyword, field, orderBy);
		request.setAttribute("providerList", providerList);
		goHome(request, response);
	}

	private void listProviders(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderBy =getOrder(request);
		List<Provider> providerList;
		if (orderBy != null) providerList = providerDAO.getAll(orderBy);
		else providerList = providerDAO.getAll();
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
		session = request.getSession();
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
	private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		String providerName = request.getParameter("name");
		String providerAddress = request.getParameter("address");
		String phoneNumber = request.getParameter("phoneNumber");
		String email = request.getParameter("email");
		String taxId = request.getParameter("taxId");

		boolean isNameValid = (providerDAO.findByName(providerName) == null);
		boolean isPhoneNumberValid = (providerDAO.findByPhoneNumber(phoneNumber) == null);
		boolean isEmailValid = (providerDAO.findByEmail(email) == null);
		boolean isTaxIdValid = (providerDAO.findByTaxId(taxId) == null);
		if (isNameValid && isPhoneNumberValid && isEmailValid && isTaxIdValid) {
			Provider provider = new Provider(providerName, providerAddress, phoneNumber, email, taxId);
			providerDAO.create(provider);
			request.setAttribute("successMessage", "Thêm nhà cung cấp thành công");
			try {
				response.sendRedirect(request.getContextPath() + "/admin/providers");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String errorMessage = "";
			boolean isFirst = true;
			if (!isNameValid) {
				errorMessage += "Tên NCC";
				isFirst = false;
			}
			if (!isPhoneNumberValid) {
				if (isFirst) errorMessage += "Số điện thoại, ";
				else {
					errorMessage += ", số điện thoại";
					isFirst = false;
				}

			}
			if (!isEmailValid) {
				if (isFirst) errorMessage += "Email";
				else {
					errorMessage += ", email";
					isFirst = false;
				}
			}
			if (!isTaxIdValid) {
				if (isFirst) errorMessage += "MST";
				else {
					errorMessage += ", MST";
					isFirst = false;
				}

			}
			errorMessage += " đã  tồn tại!";
			session.setAttribute("errorMessage", errorMessage);
			response.sendRedirect(request.getContextPath() + "/admin/providers");
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		int providerId = Integer.parseInt(request.getParameter("id"));
		// Tìm provider ứng với id
		Provider provider = providerDAO.get(providerId);
		String providerName = request.getParameter("name");
		String address = request.getParameter("address");
		String phoneNumber = request.getParameter("phoneNumber");
		String email = request.getParameter("email");
		String taxId = request.getParameter("taxId");
		session = request.getSession();

		// Lưu ý: nếu phần người dùng nhập không khác gì dữ liệu cũ thì khỏi cần find và trả về true, nếu dùng thì
		// tiếp tục kiểm tra điều kiện của find (để xem thử trùng với provider khác không)
		boolean isEmailValid =
				((Objects.equals(email, provider.getEmail())) || providerDAO.findByEmail(email) == null);
		boolean isTaxIdValid = (Objects.equals(taxId, provider.getTaxId())) || providerDAO.findByTaxId(taxId) == null;
		boolean isNameValid =
				(Objects.equals(providerName, provider.getProviderName())) || (providerDAO.findByName(providerName) == null);
		boolean isPhoneNumberValid =
				(Objects.equals(phoneNumber, provider.getPhoneNumber())) || (providerDAO.findByPhoneNumber(phoneNumber) == null);
		if (isEmailValid && isTaxIdValid && isNameValid && isPhoneNumberValid) {
			provider = new Provider(providerId, providerName, phoneNumber, address, email, taxId);
			providerDAO.update(provider);
			session.setAttribute("successMessage", "Cập nhật thành công!");
			try {
				response.sendRedirect(request.getContextPath() + "/admin/providers");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String errorMessage = "";
			boolean isFirst = true;
			if (!isNameValid) {
				errorMessage += "Tên NCC";
				isFirst = false;
			}
			if (!isPhoneNumberValid) {
				if (isFirst) errorMessage += "Số điện thoại, ";
				else {
					errorMessage += ", số điện thoại";
					isFirst = false;
				}
			}
			if (!isEmailValid) {
				if (isFirst) errorMessage += "Email";
				else {
					errorMessage += ", email";
					isFirst = false;
				}
			}
			if (!isTaxIdValid) {
				if (isFirst) errorMessage += "MST";
				else errorMessage += ", MST";
			}
			errorMessage += " đã  tồn tại!";
			session.setAttribute("errorMessage", errorMessage);
			response.sendRedirect(request.getContextPath() + "/admin/providers");
		}

	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		// Nhan parameter có name là "id"
		int providerId = Integer.parseInt(request.getParameter("id"));
		// goi ham delete
		providerDAO.delete(providerId);
		// set successMessage trong session
		session = request.getSession();
		session.setAttribute("successMessage", "Xóa nhà cung cấp thành công");
		// sendRedirect về trang chính + /admin/providers
		try {
			response.sendRedirect(request.getContextPath() + "/admin/providers");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getOrder(HttpServletRequest request){
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

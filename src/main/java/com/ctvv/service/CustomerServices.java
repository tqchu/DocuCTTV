package com.ctvv.service;

import com.ctvv.dao.CustomerDAO;
import com.ctvv.dao.ShippingAddressDAO;
import com.ctvv.model.Customer;
import com.ctvv.model.ShippingAddress;
import com.ctvv.util.PasswordHashingUtil;
import com.ctvv.util.RequestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CustomerServices {
	private final String CUSTOMER_LOGIN_SERVLET = "/login";
	private final String CUSTOMER_LOGIN_PAGE = "/customer/login/login.jsp";
	final int NUMBER_OF_RECORDS_PER_MANAGE_CUSTOMER_PAGE = 10;
	private final String MANAGE_CUSTOMER_PAGE = "/admin/manage/home.jsp";
	private CustomerDAO customerDAO = new CustomerDAO();
	private ShippingAddressDAO shippingAddressDAO = new ShippingAddressDAO();
	private HttpSession session;


	public void listCustomers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		List<Customer> customerList;
		int begin = RequestUtils.getBegin(request,NUMBER_OF_RECORDS_PER_MANAGE_CUSTOMER_PAGE);
		customerList = customerDAO.get(begin, NUMBER_OF_RECORDS_PER_MANAGE_CUSTOMER_PAGE, keyword);
		int numberOfPages = (customerDAO.count(keyword) - 1) / NUMBER_OF_RECORDS_PER_MANAGE_CUSTOMER_PAGE + 1;
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("customerList", customerList);
		request.setAttribute("tab", "customers");
		RequestDispatcher dispatcher = request.getRequestDispatcher(MANAGE_CUSTOMER_PAGE);
		dispatcher.forward(request, response);
	}

	public void changeCustomerStatus(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession();
		int customerId = Integer.parseInt(request.getParameter("id"));
		Customer customer = customerDAO.get(customerId);
		String action = request.getParameter("action");
		switch (action) {
			case "activate":
				customer.setActive(true);
				break;
			case "deactivate":
				customer.setActive(false);
				break;

		}
		customerDAO.update(customer);
		session.setAttribute("successMessage", action.equals("activate")?"Bỏ khóa":"Khóa"+" tài khoản khách hàng " +
				"thành " +
				"công");
		try {
			response.sendRedirect(request.getContextPath() + "/admin/customers");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showManageInformationPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tab = request.getParameter("tab");
		if (tab.equals("profile") || tab.equals("address") || tab.equals("password")) {
			request.setAttribute("tab", tab);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/account/manage-account.jsp");
			dispatcher.forward(request, response);
		} else
			response.sendRedirect(request.getContextPath() + "/user/account?tab=profile");
	}

	public void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		request.setAttribute("tab", "profile");
		session = request.getSession();

		String fullName = request.getParameter("fullName");
		Customer.Gender gender = Customer.Gender.valueOf(request.getParameter("gender"));
		LocalDate date_of_birth = LocalDate.parse(request.getParameter("dateOfBirth"));

		Customer customer = (Customer) session.getAttribute("customer");
		Customer updatedCustomer = new Customer(customer);
		updatedCustomer.setFullName(fullName);
		updatedCustomer.setGender(gender);
		updatedCustomer.setDateOfBirth(date_of_birth);

		updatedCustomer = customerDAO.update(updatedCustomer);
		session.setAttribute("customer", updatedCustomer);
		request.setAttribute("successMessage", "Cập nhật thành công");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/account/manage-account.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                            IOException {
		request.setAttribute("tab", "password");
		session = request.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("password");
		if (PasswordHashingUtil.validatePassword(oldPassword, customer.getPassword())) {
			//đổi mật khẩu trong database
			customer = customerDAO.update(customer);
			//đổi mật khẩu cho session hien tai
			customer.setPassword(PasswordHashingUtil.createHash(newPassword));
			request.setAttribute("successMessage", "Đổi mật khẩu thành công");
		} else {
			request.setAttribute("wrongOldPasswordMessage", "Sai mật khẩu cũ");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/account/manage-account.jsp");
		dispatcher.forward(request, response);
	}

	public void updateAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		request.setAttribute("tab", "address");
		session = request.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		ShippingAddress shippingAddress = customer.getAddress();

		// Lấy dữ liệu từ form
		String recipient_name = request.getParameter("recipientName");
		String address = request.getParameter("address");

		// Tạo 1 bản sao của shippingAddress (session)
		ShippingAddress updatedShippingAddress = new ShippingAddress(shippingAddress);
		updatedShippingAddress.setRecipientName(recipient_name);
		updatedShippingAddress.setAddress(address);

		try {
			updatedShippingAddress = shippingAddressDAO.update(updatedShippingAddress);
			customer.setAddress(updatedShippingAddress);
			//Đặt lời nhắn thành công
			request.setAttribute("successMessage", "Cập nhật thành công");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/account/manage-account.jsp");
			dispatcher.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                           IOException {
		request.setAttribute("headerAction", "Đăng nhập");
		RequestDispatcher dispatcher = request.getRequestDispatcher(CUSTOMER_LOGIN_PAGE);
		dispatcher.forward(request, response);
	}

	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		session = request.getSession();
		String from = request.getParameter("from");
		if (from.equals("")) {
			from = request.getContextPath();
		}
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		Customer customer = new Customer();
		boolean isPhoneNumber = (account.indexOf('@') == -1);
		if (isPhoneNumber) {
			customer.setPhoneNumber(account);
		} else {
			customer.setEmail(account);
		}
		customer.setPassword(password);

		Customer authenticatedCustomer;
		// TH1: tài khoản tồn tại
		authenticatedCustomer = customerDAO.validate(customer);
		if (authenticatedCustomer != null) {
			// Tài khoản bình thường (active)
			if (authenticatedCustomer.isActive()) {
				session.setAttribute("customer", authenticatedCustomer);
				String postData = (String) session.getAttribute("postData");
				if (postData != null) {
					response.setStatus(307);
					response.setHeader("Location", from);
				} else {
					response.sendRedirect(from);
				}
			}
			// Tài khoản bị khóa
			else {
				session.setAttribute("loginMessage", "Tài khoản của bạn đã bị khóa vì vi phạm chính sách của chúng " +
						"tôi");
				response.sendRedirect(request.getContextPath() + CUSTOMER_LOGIN_SERVLET);
			}

		} else {

			session.setAttribute("loginMessage", "Sai tài khoản hoặc mật khẩu");
			response.sendRedirect(request.getContextPath() + CUSTOMER_LOGIN_SERVLET);
		}
	}
}

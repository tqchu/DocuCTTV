package com.ctvv.controller.customer;

import com.ctvv.dao.CustomerDAO;
import com.ctvv.model.Customer;
import com.ctvv.model.ShippingAddress;
import com.ctvv.util.EmailUtils;
import com.ctvv.util.SMSUtils;
import com.ctvv.util.StringUtils;
import com.ctvv.util.UniqueStringUtils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "CustomerRegisterController", value = "/register")
public class CustomerRegisterController
		extends HttpServlet {

	private static final String HOME_SERVLET = "/register";
	private final String HOME_PAGE = "/customer/register/register.jsp";
	private final String PHONE_OTP_PAGE = "/customer/register/otp.jsp";
	private final String EMAIL_PAGE = "/customer/register/email.jsp";
	private final String EMAIL_OTP_PAGE = "/customer/register/otpEmail.jsp";
	private final String SETUP_PAGE = "/customer/register/setUp.jsp";
	private HttpSession session;
	private CustomerDAO customerDAO;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		// Dispatch về home_page
		if (customer == null) {
			String registerPhase = (String) session.getAttribute("registerPhase");
			String page = "";
			if (registerPhase == null) {
				page = HOME_PAGE;
			} else {
				switch (registerPhase) {
					case "phone-otp":
						page = PHONE_OTP_PAGE;
						break;
					case "email":
						page = EMAIL_PAGE;
						break;
					case "email-otp":
						page = EMAIL_OTP_PAGE;
						break;
					case "set-up":
						page = SETUP_PAGE;
						break;
				}
				session.removeAttribute("registerPhase");
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(page);
			dispatcher.forward(request, response);
		}
		// Redirect về trang home nếu đã đăng nhập
		else response.sendRedirect(request.getContextPath());
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String phase = request.getParameter("phase");
		switch (phase) {
			case "phone":
				sendSMSOTP(request, response);
				break;
			case "otp-phone":
				verifyPhoneOTP(request, response);
				break;
			case "email":
				sendMailOTP(request, response);
				break;
			case "otp-email":
				verifyMailOTP(request, response);
				break;
			case "set-up":
				register(request, response);
				break;
		}

		redirectToHome(request, response);
	}

	private void sendSMSOTP(HttpServletRequest request, HttpServletResponse response) {
		String phoneNumber = request.getParameter("phoneNumber");

		phoneNumber = StringUtils.regionPhoneNumber(phoneNumber);
		session.setAttribute("phoneNumber", StringUtils.phoneNumberBeginWithZero(phoneNumber));
		// Tạo otp
		String otp = UniqueStringUtils.otp();
		SMSUtils.send(phoneNumber, otp);
		session.setAttribute("otp", otp);
		session.setAttribute("expireTime", LocalDateTime.now().plusSeconds(SMSUtils.otpTime));
		session.setAttribute("registerPhase", "phone-otp");
	}

	private void verifyPhoneOTP(HttpServletRequest request, HttpServletResponse response) {
		String otpParam = request.getParameter("otp");
		LocalDateTime expireTime = (LocalDateTime) session.getAttribute("expireTime");
		// Trường hợp otp còn hiệu lực
		if (!expireTime.isBefore(LocalDateTime.now())) {
			// Success
			if (otpParam.equals(session.getAttribute("otp"))) {

				Customer customer = customerDAO.findCustomerByPhoneNumber((String) session.getAttribute("phoneNumber"
				));
				if (customer != null) {
					session.setAttribute("duplicatePhoneNumberMessage", "Số điện thoại này đã được đăng ký");
					session.setAttribute("substituteCustomer", customer);
					session.setAttribute("registerPhase", "phone-otp");
				} else
					// Chuyển tới trang email
					session.setAttribute("registerPhase", "email");
			}
			// Sai otp
			else {
				session.setAttribute("registerPhase", "phone-otp");
				session.setAttribute("errorMessage", "Sai OTP");
			}
		}
		// Trường hợp otp đã hết hạn
		else {
			session.setAttribute("registerPhase", "phone-otp");
			session.setAttribute("errorMessage", "OTP đã hết hạn");
		}
	}

	private void sendMailOTP(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");
		Customer customer = null;
		customer = customerDAO.findCustomerByEmail(email);
		if (customer != null) {
			session.setAttribute("registerPhase", "email");
			session.setAttribute("errorMessage", "Email đã được sử dụng");
		} else {
			session.setAttribute("email", email);
			// Tạo otp
			String otp = UniqueStringUtils.otp();
			EmailUtils.sendOTP(email, otp);
			session.setAttribute("otp", otp);
			session.setAttribute("expireTime", LocalDateTime.now().plusSeconds(SMSUtils.otpTime));
			session.setAttribute("registerPhase", "email-otp");
		}

	}

	private void verifyMailOTP(HttpServletRequest request, HttpServletResponse response) {
		String otpParam = request.getParameter("otp");
		LocalDateTime expireTime = (LocalDateTime) session.getAttribute("expireTime");
		// Trường hợp otp còn hiệu lực
		if (!expireTime.isBefore(LocalDateTime.now())) {
			// Success
			if (otpParam.equals(session.getAttribute("otp"))) {
					// Chuyển tới trang thiết lập tài khoản
					session.setAttribute("registerPhase", "set-up");
			}
			// Sai otp
			else {
				session.setAttribute("registerPhase", "email-otp");
				session.setAttribute("errorMessage", "Sai OTP");
			}
		}
		// Trường hợp otp đã hết hạn
		else {
			session.setAttribute("registerPhase", "email-otp");
			session.setAttribute("errorMessage", "OTP đã hết hạn");
		}
	}

	private void register(HttpServletRequest request, HttpServletResponse response) {
		String phoneNumber = (String) session.getAttribute("phoneNumber");
		session.removeAttribute("phoneNumber");
		String fullName = request.getParameter("fullName");
		String password = request.getParameter("password");
		String dateOfBirthParam = request.getParameter("dateOfBirth");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateOfBirth = LocalDate.parse(dateOfBirthParam, formatter);
		String genderParam = request.getParameter("gender");
		Customer.Gender gender = null;
		switch (genderParam) {
			case "male":
				gender = Customer.Gender.MALE;
				break;
			case "female":
				gender = Customer.Gender.FEMALE;
				break;
			case "undefined":
				gender = Customer.Gender.OTHER;
				break;

		}
		String address = request.getParameter("address");
		ShippingAddress shippingAddress = new ShippingAddress(fullName,phoneNumber,address);
		String email = (String) session.getAttribute("email");
		session.removeAttribute("email");
		Customer customer = new Customer(password, gender, fullName, phoneNumber, dateOfBirth, email,shippingAddress);
		session.setAttribute("customer", customerDAO.create(customer));

		// Khi register tạo shipping address dựa vào address, sdt, tên
		//		customer.setAddress();
	}

	private void redirectToHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getContextPath() + HOME_SERVLET);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			customerDAO = new CustomerDAO(dataSource);

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}

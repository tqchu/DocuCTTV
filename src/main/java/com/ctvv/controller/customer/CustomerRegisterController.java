package com.ctvv.controller.customer;

import com.ctvv.dao.CustomerDAO;
import com.ctvv.model.Customer;
import com.ctvv.util.SMSUtils;
import com.ctvv.util.StringUtils;
import com.ctvv.util.UniqueStringUtils;
import org.checkerframework.checker.units.qual.C;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "CustomerRegisterController", value = "/register")
public class CustomerRegisterController
		extends HttpServlet {

	private static final String HOME_SERVLET = "/register";
	private final String HOME_PAGE = "/customer/register/register.jsp";
	private final String OTP_PAGE = "/customer/register/otp.jsp";
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
			String registerAction = (String) session.getAttribute("registerAction");
			String page = "";
			if (registerAction == null) {
				page = HOME_PAGE;
			} else {
				switch (registerAction) {
					case "otp":
						page = OTP_PAGE;
						break;
					case "setUp":
						page = SETUP_PAGE;
						break;
				}
				session.removeAttribute("registerAction");
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
		String phoneNumber = request.getParameter("phoneNumber");
		String otpParam = request.getParameter("otp");
		// Yêu cầu lấy mã otp
		if (phoneNumber != null) {
			sendOtp(request, response);

		} else if (otpParam != null) {
			verifyOtp(request, response);
		} else {
			register(request, response);
		}
		// Set up

		redirectToHome(request, response);
	}

	private void sendOtp(HttpServletRequest request, HttpServletResponse response) {
		String phoneNumber = request.getParameter("phoneNumber");

		phoneNumber = StringUtils.regionPhoneNumber(phoneNumber);
		session.setAttribute("phoneNumber", StringUtils.phoneNumberBeginWithZero(phoneNumber));
		// Tạo otp
		String otp = UniqueStringUtils.otp();
		SMSUtils.send(phoneNumber, otp);
		session.setAttribute("otp", otp);
		session.setAttribute("expireTime", LocalDateTime.now().plusSeconds(SMSUtils.otpTime));
		session.setAttribute("registerAction", "otp");
	}

	private void verifyOtp(HttpServletRequest request, HttpServletResponse response) {
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
					session.setAttribute("substituteCustomer", customer );
					session.setAttribute("registerAction", "otp");
				}
				else
				// Chuyển tới trang thiết lập mật khẩu
				session.setAttribute("registerAction", "setUp");
			}
			// Sai otp
			else {
				session.setAttribute("registerAction", "otp");
				session.setAttribute("errorMessage", "Sai OTP");
			}
		}
		// Trường hợp otp đã hết hạn
		else {
			session.setAttribute("registerAction", "otp");
			session.setAttribute("errorMessage", "OTP đã hết hạn");
		}
	}

	private void register(HttpServletRequest request, HttpServletResponse response) {
		String phoneNumber = (String) session.getAttribute("phoneNumber");
		session.removeAttribute("phoneNumber");
		String fullName = request.getParameter("fullName");
		String password = request.getParameter("password");
		String dateOfBirthParam = request.getParameter("dateOfBirth");
		String genderParam = request.getParameter("gender");
		Customer.Gender gender = null;
		String address = request.getParameter("address");
		Customer customer = new Customer();
		customer.setPhoneNumber(phoneNumber);
		customer.setFullName(fullName);
		customer.setPassword(password);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateOfBirth = LocalDate.parse(dateOfBirthParam, formatter);
		customer.setDateOfBirth(dateOfBirth);
		switch (genderParam){
			case "male":
				gender = Customer.Gender.MALE;
				break;
			case "female":
				gender= Customer.Gender.FEMALE;
				break;
			case "undefined":
				gender = Customer.Gender.OTHER;
				break;

		}
		customer.setGender(gender);
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

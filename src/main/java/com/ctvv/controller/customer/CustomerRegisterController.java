package com.ctvv.controller.customer;

import com.ctvv.dao.CustomerDAO;
import com.ctvv.model.Customer;
import com.ctvv.model.ShippingAddress;
import com.ctvv.service.CustomerServices;
import com.ctvv.util.EmailUtils;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "CustomerRegisterController", value = "/register")
public class CustomerRegisterController
		extends HttpServlet {

	private static final String HOME_SERVLET = "/register";
	private CustomerServices customerServices = new CustomerServices();
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		// Dispatch về home_page
		if (customer == null) {
			String registerPhase = (String) session.getAttribute("registerPhase");
			String page = "";

			if (registerPhase == null) {
				String HOME_PAGE = "/customer/register/register.jsp";
				page = HOME_PAGE;
			} else {
				String PHONE_OTP_PAGE = "/customer/register/otp.jsp";
				String EMAIL_PAGE = "/customer/register/email.jsp";
				String EMAIL_OTP_PAGE = "/customer/register/otpEmail.jsp";
				String SETUP_PAGE = "/customer/register/setUp.jsp";
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
		String phase = request.getParameter("phase");
		if (phase.equals("takeBackAccount")) {
			customerServices.takeBackAccount(request, response);

		} else
			switch (phase) {
				case "phone":
					customerServices.sendSMSOTP(request, response);
					break;
				case "otp-phone":
					customerServices.verifyPhoneOTP(request, response);
					break;
				case "email":
					customerServices.sendMailOTP(request, response);
					break;
				case "otp-email":
					customerServices.verifyMailOTP(request, response);
					break;
				case "set-up":
					customerServices.register(request, response);
					break;
			}

		redirectToHome(request, response);
	}

	private void redirectToHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getContextPath() + HOME_SERVLET);
	}

}

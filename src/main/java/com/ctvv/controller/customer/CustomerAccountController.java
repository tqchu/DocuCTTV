package com.ctvv.controller.customer;


import java.time.LocalDate;

import com.ctvv.dao.ShippingAddressDAO;
import com.ctvv.model.Customer;
import com.ctvv.model.ShippingAddress;
import com.ctvv.dao.CustomerDAO;
import com.ctvv.service.CustomerServices;
import com.ctvv.util.PasswordHashingUtil;
import org.jboss.weld.bean.SessionBean;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

@WebServlet(name = "CustomerAccountController", value = "/user/account")
public class CustomerAccountController
		extends HttpServlet {

	private final CustomerServices customerServices = new CustomerServices();

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tab = request.getParameter("tab");
		if (tab != null) {
			customerServices.showManageInformationPage(request, response);

		} else response.sendRedirect(request.getContextPath() + "/user/account?tab=profile");
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String action = request.getParameter("action");
		switch (action) {
			case "updateProfile":
				customerServices.updateProfile(request, response);
				break;
			case "changePassword":
				customerServices.changePassword(request, response);
				break;

			case "updateAddress":
				customerServices.updateAddress(request, response);
				break;

		}
	}


}





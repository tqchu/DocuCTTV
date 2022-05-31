package com.ctvv.controller.admin;

import com.ctvv.dao.CustomerDAO;
import com.ctvv.model.Customer;
import org.glassfish.jersey.internal.inject.Custom;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ManageCustomersController", value = "/admin/customers/*")
public class ManageCustomersController
		extends HttpServlet {
	private final  String HOME_PAGE = "/admin/manage/home.jsp";
	private CustomerDAO customerDAO;
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Customer> customerList  = new ArrayList<>();
		Customer customer = new Customer();
		customer.setEmail("truongquangchu.tqc@gmail.com");
		customer.setPassword("dochautrinh");
		customerList.add(customerDAO.validate(customer));
		request.setAttribute("customerList", customerList);
		goHome(request, response);
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("tab", "customers");
		RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_PAGE);
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	public void init() throws ServletException {
		super.init();
		try{
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			customerDAO = new CustomerDAO(dataSource);
		}
		catch (NamingException e){
			e.printStackTrace();
		}
	}
}

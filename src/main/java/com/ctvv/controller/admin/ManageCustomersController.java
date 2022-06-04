package com.ctvv.controller.admin;

import com.ctvv.dao.CustomerDAO;
import com.ctvv.model.Customer;
import com.ctvv.model.Provider;
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
	final int NUMBER_OF_RECORDS_PER_PAGE = 10;
	private CustomerDAO customerDAO;
	private final  String HOME_PAGE = "/admin/manage/home.jsp";
	private  final String HOME_SERVLET = "/admin/customers";
	HttpSession session;
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			listCustomers(request, response);
	}

	private void listCustomers(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		List<Customer> customerList;
		int begin = getBegin(request);
		customerList = customerDAO.get(begin, NUMBER_OF_RECORDS_PER_PAGE, keyword);
		int numberOfPages = (customerDAO.count(keyword) - 1) / NUMBER_OF_RECORDS_PER_PAGE + 1;
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("customerList", customerList);
		goHome(request, response);
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("tab", "customers");
		RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_PAGE);
		dispatcher.forward(request, response);
	}

	public int getBegin(HttpServletRequest request){
		String pageParam = request.getParameter("page");
		int page;
		if(pageParam == null){
			page = 1;
		} else {
			page = Integer.parseInt(pageParam);
		}
		return NUMBER_OF_RECORDS_PER_PAGE * (page - 1);
	}


	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

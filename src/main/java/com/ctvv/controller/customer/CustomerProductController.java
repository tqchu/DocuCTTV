package com.ctvv.controller.customer;

import com.ctvv.dao.ProductDAO;
import com.ctvv.model.Product;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "CustomerProductController", value = "/product")
public class CustomerProductController
		extends HttpServlet {
	private ProductDAO productDAO;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.get(id);
		request.setAttribute("product", product);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/home/product.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	public void init() throws ServletException {
		super.init();
		Context context = null;
		try {
			context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			productDAO = new ProductDAO(dataSource);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}

package com.ctvv.controller.admin;

import com.ctvv.dao.ProductDAO;
import com.ctvv.model.Product;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManageProductsController", value = "/admin/products")
public class ManageProductsController
		extends HttpServlet {
	private ProductDAO productDAO;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        listProducts(request,response);
	}

	private void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                           IOException {
		List<Product> productList = productDAO.getAll();
		request.setAttribute("list", productList);
		goHome(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("tab", "products");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/home.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Context context = new InitialContext();
			// Tạo và gán dataSource cho adminDAO
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			productDAO = new ProductDAO(dataSource);
		} catch (NamingException e) {

			e.printStackTrace();
		}
	}
}

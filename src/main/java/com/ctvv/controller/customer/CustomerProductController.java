package com.ctvv.controller.customer;

import com.ctvv.dao.CategoryDAO;
import com.ctvv.dao.ProductDAO;
import com.ctvv.model.CartItem;
import com.ctvv.model.Category;
import com.ctvv.model.Product;
import com.ctvv.service.CartServices;
import com.ctvv.service.ProductServices;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerProductController", value = "/products/*")
public class CustomerProductController
		extends HttpServlet {
	private final ProductServices productServices = new ProductServices();
	private  CartServices cartServices;
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		cartServices = new CartServices();
		cartServices.initCart(request, response);
		// Search
		if (request.getRequestURI().equals(request.getContextPath() + "/products/search")) productServices.search(request, response);
		else {
			productServices.viewProducts(request, response);
		}

	}

}

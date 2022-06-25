package com.ctvv.controller.customer;

import com.ctvv.dao.ProductDAO;
import com.ctvv.model.CartItem;
import com.ctvv.model.Product;
import com.ctvv.service.CartServices;

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

@WebServlet(name = "CustomerCartController", value = "/user/cart")
public class CustomerCartController
		extends HttpServlet {
	private final CartServices cartServices = new CartServices();
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		cartServices.showManageCartPage(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {
				case "add":
					cartServices.addToCart(request, response);
					break;
				case "update":
					cartServices.updateCartItem(request, response);
					break;
				case "delete":
					cartServices.deleteCartItem(request, response);
					break;
			}
		}
	}

}

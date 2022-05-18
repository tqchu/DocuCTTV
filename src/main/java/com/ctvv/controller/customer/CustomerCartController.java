package com.ctvv.controller.customer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CustomerCartController", value = "/user/cart")
public class CustomerCartController
		extends HttpServlet {
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		goHome(request, response);
		// Lưu item -> id, quantity
		//
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/cart/cart.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action!=null) {
			switch (action) {
				case "add":
					addToCart(request, response);
					break;
				case "update":
					updateCartItem(request, response);
					break;
				case "delete":
					deleteCartItem(request, response);
					break;
			}
		}
	}

	private void deleteCartItem(HttpServletRequest request, HttpServletResponse response) {
	}

	private void updateCartItem(HttpServletRequest request, HttpServletResponse response) {
	}

	private void addToCart(HttpServletRequest request, HttpServletResponse response) {
	}
}

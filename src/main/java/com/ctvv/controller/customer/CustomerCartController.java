package com.ctvv.controller.customer;

import com.ctvv.dao.CategoryDAO;
import com.ctvv.dao.ProductDAO;
import com.ctvv.model.CartItem;
import com.ctvv.model.Product;

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
	HttpSession session;
	private ProductDAO productDAO;
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
		session = request.getSession();
		int productId = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.get(productId);
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		CartItem cartItem = new CartItem(product,quantity);
		if ((List<CartItem>) session.getAttribute("cartList")!=null){
			List<CartItem> cartItemList = (List<CartItem>) session.getAttribute("cartList");
			int pos = isExistedInCart(cartItemList,productId);
			if (pos>=0){
				cartItemList.get(pos).setQuantity(cartItemList.get(pos).getQuantity()+quantity);
			}
			else {
				cartItemList.add(cartItem);
			}
			session.setAttribute("cartList",cartItemList);
		}
		else {
			List<CartItem> cartItemList = new ArrayList<>();
			cartItemList.add(cartItem);
			session.setAttribute("cartList",cartItemList);
		}
	}
	private int isExistedInCart(List<CartItem> cartItemList, int productId){
		for (int i = 0;i<cartItemList.size();i++){
			if (cartItemList.get(i).getProduct().getProductId()==productId){
				return i;
			}
		}
		return -1;
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

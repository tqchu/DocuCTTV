package com.ctvv.controller.customer;

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
		session= request.getSession();
		goHome(request, response);
		// Lưu item -> id, quantity
		//
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/cart/cart.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action != null) {
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

	private void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		session = request.getSession();
		int productId = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.get(productId);
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		CartItem cartItem = new CartItem(product, quantity);
		List<CartItem> cartItemList = (List<CartItem>) session.getAttribute("cart");
		// TH1: Cart List đã được khởi tạo
		if (cartItemList != null) {
			// Xác định cart item đã tồn tại trong list chưa, nếu có rồi thì thêm số lượng
			int pos = findItemInCart(cartItemList, productId);
			if (pos >= 0) {
				CartItem item = cartItemList.get(pos);
				item.setQuantity(item.getQuantity() + quantity);
			} else {
				cartItemList.add(cartItem);
			}
			session.setAttribute("cart", cartItemList);
		}
		// TH2: Cart rỗng
		else {
			cartItemList = new ArrayList<>();
			cartItemList.add(cartItem);
			session.setAttribute("cart", cartItemList);
		}

		String from = request.getParameter("from");
		session.setAttribute("cartSuccessMessage", "Thêm vào giỏ hàng thành công");
		response.sendRedirect(from);

	}

	private void updateCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		session = request.getSession();
		int productId = Integer.parseInt(request.getParameter("id"));
		List<CartItem> cartItemList= (List<CartItem>) session.getAttribute("cart");
		int newquantity = Integer.parseInt(request.getParameter("quantity"));
		if (newquantity>quantityInStock(productId)){
			request.setAttribute("outOfStockMessage","");
		}
		for (CartItem item : cartItemList){
			if (item.getProduct().getProductId() == productId){
				item.setQuantity(newquantity);
				break;
			}
		}
		session.setAttribute("cart",cartItemList);
		String from = request.getParameter("from");
		response.sendRedirect(from);
	}

	private void deleteCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException{
			session = request.getSession();
			String [] productIds = request.getParameterValues("id");
			List<CartItem> cartItemList = (List<CartItem>)session.getAttribute("cart");
			for (int i = 0;i<productIds.length;i++){
				int productId = Integer.parseInt(productIds[i]);
				cartItemList.remove(findItemInCart(cartItemList,productId));
			}
			session.setAttribute("cart",cartItemList);

	}

	private int findItemInCart(List<CartItem> cartItemList, int productId) {
		for (int i = 0; i < cartItemList.size(); i++) {
			if (cartItemList.get(i).getProduct().getProductId() == productId) {
				return i;
			}
		}
		return -1;
	}
	private int quantityInStock(int productId){
		//return importDAO.getQuantity(productId) - orderDAO.getQuantity(productId);
		return 0;
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

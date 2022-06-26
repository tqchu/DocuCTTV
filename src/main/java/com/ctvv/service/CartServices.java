package com.ctvv.service;

import com.ctvv.dao.ProductDAO;
import com.ctvv.dao.StockItemDAO;
import com.ctvv.model.CartItem;
import com.ctvv.model.Product;
import com.ctvv.model.StockItem;
import com.ctvv.util.MultiMapUtils;
import com.google.common.collect.Multimap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartServices {
	private static final String CHECKOUT_PAGE = "/customer/checkout/checkout.jsp";
	private ProductDAO productDAO = new ProductDAO();
	private StockItemDAO stockItemDAO = new StockItemDAO();
	private HttpSession session;

	public void checkout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		session = request.getSession();
		String[] idParams;
		String[] quantityParams;
		String postData = (String) session.getAttribute("postData");
		if (postData != null) {
			Multimap<String, String> params = MultiMapUtils.convertToQueryStringToMultimap(postData);
			idParams = params.get("id").toArray(new String[0]);
			quantityParams = params.get("quantity").toArray(new String[0]);
			session.removeAttribute("postData");
		} else {

			idParams = request.getParameterValues("id");
			quantityParams = request.getParameterValues("quantity");
		}
		long totalPrice = 0;
		boolean isOutOfStock = false;
		List<CartItem> checkoutList = new ArrayList<>();
		for (int i = 0; i < idParams.length; i++) {
			int id = Integer.parseInt(idParams[i]);
			StockItem stockItem = stockItemDAO.get(id);
			int quantity = Integer.parseInt(quantityParams[i]);
			if (quantity > stockItem.getQuantity()) {
				// set errorMessage cho từng product
				session.setAttribute("outOfStock" + id,
						"Số lượng sản phẩm chỉ còn lại " + stockItem.getQuantity() + " , vui lòng chọn số lượng ít " +
								"hơn!");
				isOutOfStock = true;
			}
			Product product = productDAO.get(id);
			checkoutList.add(new CartItem(product, quantity));
			totalPrice += (long) product.getPrice() * quantity;

		}
		if (isOutOfStock) {
			response.sendRedirect(request.getContextPath() + "/user/cart");
		} else {
			request.setAttribute("totalPrice", totalPrice);
			request.setAttribute("shippingFee", 200000);

			request.setAttribute("checkoutList", checkoutList);
			RequestDispatcher dispatcher = request.getRequestDispatcher(CHECKOUT_PAGE);
			dispatcher.forward(request, response);
		}

	}

	public void showManageCartPage(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                                IOException {
		session = request.getSession();
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		if (cart != null) {
			for (CartItem cartItem : cart) {
				cartItem.setProduct(productDAO.get(cartItem.getProduct().getProductId()));
			}

		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/cart/cart.jsp");
		dispatcher.forward(request, response);
	}

	public void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
				// chuyển lên đầu
				cartItemList.remove(item);
				cartItemList.add(0, item);

			} else {
				cartItemList.add(0, cartItem);
			}
		}
		// TH2: Cart rỗng
		else {
			cartItemList = new ArrayList<>();
			cartItemList.add(0, cartItem);
			session.setAttribute("cart", cartItemList);
		}
		// Buy-now
		boolean isBuyNowOption = request.getParameter("buy-now") != null;
		if (isBuyNowOption) {
			session.setAttribute("isBuyNow", true);
			response.sendRedirect(request.getContextPath() + request.getServletPath());
		} else {
			String from = request.getParameter("from");
			session.setAttribute("cartSuccessMessage", "Thêm vào giỏ hàng thành công");
			response.sendRedirect(from);
		}
	}
	private int findItemInCart(List<CartItem> cartItemList, int productId) {
		for (int i = 0; i < cartItemList.size(); i++) {
			if (cartItemList.get(i).getProduct().getProductId() == productId) {
				return i;
			}
		}
		return -1;
	}

	public void updateCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		int newQuantity = Integer.parseInt(request.getParameter("newQuantity"));

		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		for (CartItem cartItem : cart) {
			if (cartItem.getProduct().getProductId() == id) {
				cartItem.setQuantity(newQuantity);
				break;
			}
		}

		response.sendRedirect(request.getContextPath() + request.getServletPath());
	}

	public void deleteCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String[] idParams = request.getParameterValues("id");
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		for (String idParam : idParams) {
			int productId = Integer.parseInt(idParam);
			cart.remove(findItemInCart(cart, productId));
		}

		response.sendRedirect(request.getContextPath() + request.getServletPath());
	}

	public void initCart(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession();
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		if (cart != null) {
			for (CartItem cartItem : cart) {
				cartItem.setProduct(productDAO.get(cartItem.getProduct().getProductId()));
			}

		}
	}
}

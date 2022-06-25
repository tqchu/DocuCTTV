package com.ctvv.controller.customer;

import com.ctvv.dao.ProductDAO;
import com.ctvv.dao.StockItemDAO;
import com.ctvv.model.CartItem;
import com.ctvv.model.Product;
import com.ctvv.model.StockItem;
import com.ctvv.util.MultiMapUtils;
import com.google.common.collect.Multimap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CheckoutController", value = "/checkout")
public class CheckoutController
		extends HttpServlet {
	private static final String HOME_PAGE = "/customer/checkout/checkout.jsp";
	private ProductDAO productDAO;
	private StockItemDAO stockItemDAO;
	private HttpSession session;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			goHome(request, response);
		}

	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_PAGE);
		dispatcher.forward(request, response);
	}

	@Override
	public void init() throws ServletException {
		super.init();
			productDAO = new ProductDAO();
			stockItemDAO = new StockItemDAO();
	}
}

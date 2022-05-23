package com.ctvv.controller.customer;

import com.ctvv.dao.ProductDAO;
import com.ctvv.dao.StockItemDAO;
import com.ctvv.model.CartItem;
import com.ctvv.model.Product;
import com.ctvv.model.StockItem;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
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
		String[] idParams = request.getParameterValues("id");
		String[] quantityParams = request.getParameterValues("quantity");
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
		if (isOutOfStock){
			response.sendRedirect(request.getContextPath()+"/user/cart");
		}
		else{
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
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			productDAO = new ProductDAO(dataSource);
			stockItemDAO = new StockItemDAO(dataSource);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}

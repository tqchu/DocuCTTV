package com.ctvv.controller.customer;

import com.ctvv.dao.ProductDAO;
import com.ctvv.dao.StockItemDAO;
import com.ctvv.model.CartItem;
import com.ctvv.model.Product;
import com.ctvv.model.StockItem;
import com.ctvv.service.CartServices;
import com.ctvv.service.OrderServices;
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
	private final CartServices cartServices = new CartServices();

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		cartServices.checkout(request, response);
	}

}

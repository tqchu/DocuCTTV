package com.ctvv.controller.customer;

import com.ctvv.dao.CategoryDAO;
import com.ctvv.dao.ProductDAO;
import com.ctvv.model.CartItem;
import com.ctvv.model.Category;
import com.ctvv.model.Customer;
import com.ctvv.model.Product;
import com.ctvv.service.CartServices;
import com.ctvv.service.ProductServices;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerHomeController", value = "")
public class CustomerHomeController
		extends HttpServlet {

	private final ProductServices productServices = new ProductServices();
	private CartServices cartServices ;
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		cartServices = new CartServices();
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {

			session.setAttribute("customer", session.getAttribute("substituteCustomer"));
		}
		session.removeAttribute("substituteCustomer");
		session.removeAttribute("oldCustomer");
		cartServices.initCart(request, response);
		productServices.listProductAndCategory(request, response);
	}

}

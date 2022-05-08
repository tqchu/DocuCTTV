package com.ctvv.controller.customer;

import com.ctvv.dao.CategoryDAO;
import com.ctvv.dao.ProductDAO;
import com.ctvv.model.Category;
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

@WebServlet(name = "CustomerProductController", value = "/products/*")
public class CustomerProductController
		extends HttpServlet {
	private ProductDAO productDAO;
	private CategoryDAO categoryDAO;
	private final int NUMBER_OF_RECORDS_PER_PAGE = 10;
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("requestURI",request.getRequestURI());
		if (request.getRequestURI().equals(request.getContextPath()+ "products/search")) search(request,response);
		else {
			String categoryName = request.getParameter("category");
			String idParam = request.getParameter("id");
			if (categoryName != null) {
				Category category = categoryDAO.find(categoryName);
				List<Product> productList = new ArrayList<>();
				String sortBy = getSortBy(request);
				String order = request.getParameter("order");
				int begin = getBegin(request);
				productList = productDAO.getAllByCategory(category.getCategoryId(),sortBy,order,begin,NUMBER_OF_RECORDS_PER_PAGE);
				request.setAttribute("productList", productList);
				List<Category> categoryList = categoryDAO.getAll();
				request.setAttribute("categoryList", categoryList);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/home/home.jsp");
				dispatcher.forward(request, response);
			} else if (idParam != null) {
				int id = Integer.parseInt(idParam);
				Product product = productDAO.get(id);
				request.setAttribute("product", product);
				if (product.getCategory() != null) {
					List<Product> similarProducts = productDAO.getAllByCategory(product.getCategory().getCategoryId(),null,null,0,NUMBER_OF_RECORDS_PER_PAGE);
					similarProducts.remove(product);
					request.setAttribute("similarProducts", similarProducts);
				}
				RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/home/product.jsp");
				dispatcher.forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath());
			}
		}

	}

	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String keyword = request.getParameter("keyword");
		String sortBy = getSortBy(request);
		String order = request.getParameter("order");
		int begin = getBegin(request);
		int minPrice ;
		int maxPrice ;
		if (request.getParameter("minPrice")!=null) minPrice= Integer.parseInt(request.getParameter("minPrice"));
		else minPrice=0;
		if  (request.getParameter("maxPrice")!=null) maxPrice= Integer.parseInt(request.getParameter("maxPrice"));
		else maxPrice=Integer.MAX_VALUE;

		List<Product> productList;
		productList = productDAO.get(begin, NUMBER_OF_RECORDS_PER_PAGE, keyword, minPrice, maxPrice, sortBy, order);

		int numberOfPages = (productDAO.count(keyword, minPrice, maxPrice) - 1) / NUMBER_OF_RECORDS_PER_PAGE + 1;
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("search", true);
		request.setAttribute("productList", productList);
		goHome(request, response);
	}

	public String getSortBy(HttpServletRequest request) {
		String sortBy = request.getParameter("sortBy");
		if (sortBy != null) {
			switch (sortBy) {
				case "price":
					sortBy = "price";
					break;
				default:
					sortBy = null;
					break;
			}
		}
		return sortBy;
	}
	public int getBegin(HttpServletRequest request) {
		String pageParam = request.getParameter("page");
		int page;
		if (pageParam == null) {
			page = 1;
		} else {
			page = Integer.parseInt(pageParam);
		}
		return NUMBER_OF_RECORDS_PER_PAGE * (page - 1);
	}
	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/home/home.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	public void init() throws ServletException {
		super.init();
		Context context = null;
		try {
			context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			productDAO = new ProductDAO(dataSource);
			categoryDAO = new CategoryDAO(dataSource);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}

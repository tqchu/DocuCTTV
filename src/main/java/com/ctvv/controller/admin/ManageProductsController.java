package com.ctvv.controller.admin;

import com.ctvv.dao.CategoryDAO;
import com.ctvv.dao.ProductDAO;
import com.ctvv.model.Category;
import com.ctvv.model.Product;
import com.ctvv.service.ProductServices;
import org.checkerframework.checker.units.qual.C;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
		maxFileSize = 1024 * 1024 * 5,
		maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "ManageProductsController", value = "/admin/products/*")
public class ManageProductsController
		extends HttpServlet {
	private final ProductServices productServices = new ProductServices();
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoryDAO categoryDAO = new CategoryDAO();
		ProductDAO productDAO = new ProductDAO();
		String action = request.getParameter("action");
		if (action != null) {
			String path = "";
			List<Category> categoryList = categoryDAO.getAll("category_name", "ASC");
			request.setAttribute("categoryList", categoryList);
			switch (action) {
				case "create":
					path = "/admin/manage/product/addForm.jsp";
					break;
				case "view":
					int id = Integer.parseInt(request.getParameter("id"));
					Product product = productDAO.get(id);
					request.setAttribute("product", product);
					request.setAttribute("categoryList", categoryList);
					path = "/admin/manage/product/view_editForm.jsp";
					break;
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		} else {
			productServices.listProducts(request, response);
		}
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
			case "create":
				productServices.create(request, response);
				break;
			case "update":
				productServices.update(request, response);
				break;
			case "delete":
				productServices.delete(request, response);
				break;
		}
	}

}

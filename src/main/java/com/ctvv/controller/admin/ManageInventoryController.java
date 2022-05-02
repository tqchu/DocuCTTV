package com.ctvv.controller.admin;

import com.ctvv.dao.ImportDAO;
import com.ctvv.model.Category;
import com.ctvv.model.Import;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManageInventoryController", value = "/admin/inventory/*")
public class ManageInventoryController
		extends HttpServlet {
	private ImportDAO importDAO;
	final int NUMBER_OF_RECORDS_PER_PAGE = 5  ;
	final String HOME_PAGE = "/admin/manage/home.jsp";

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		goHome(request, response);
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		if (request.getRequestURI().equals(request.getContextPath()+"/admin/inventory/history")) {
			request.setAttribute("tab", "inventoryHistory");
			listImport(request,response);
			request.setAttribute("requestURI", request.getRequestURI());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/home.jsp");
			dispatcher.forward(request, response);
		}
		else
			request.setAttribute("tab", "inventory");

	}
	private void listImport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		String orderBy = getOrder(request);
		List<Import> importList;
		int begin = getBegin(request);
		importList = importDAO.get(begin, NUMBER_OF_RECORDS_PER_PAGE, keyword, orderBy, null);
		//int numberOfPages = importDAO.count(keyword) / NUMBER_OF_RECORDS_PER_PAGE + 1;
		//request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("importList", importList);
		//goHome(request, response);
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
	public String getOrder(HttpServletRequest request) {
		String orderBy = request.getParameter("orderBy");
		if (orderBy != null) {
			switch (orderBy) {
				case "default":
					orderBy = null;
					break;
				case "name":
					orderBy = "provider_name";
					break;
			}
		}
		return orderBy;
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			importDAO= new ImportDAO(dataSource);
		} catch (NamingException e){
			e.printStackTrace();
		}
	}
}

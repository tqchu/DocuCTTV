package com.ctvv.controller.admin;

import com.ctvv.dao.ImportDAO;
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
	final String HOME_PAGE = "/admin/manage/home.jsp";

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		goHome(request, response);
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		if (request.getRequestURI().equals(request.getContextPath()+"/admin/inventory/history")) {
//			List<Import> importList =  importDAO.get
			request.setAttribute("tab", "inventoryHistory");
		}
		else
			request.setAttribute("tab", "inventory");
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(HOME_PAGE);
		requestDispatcher.forward(request, response);
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

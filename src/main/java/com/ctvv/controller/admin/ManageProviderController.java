package com.ctvv.controller.admin;

import com.ctvv.dao.ProviderDAO;
import com.ctvv.model.Provider;
import com.ctvv.service.ProviderServices;

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
import java.util.List;
import java.util.Objects;

@WebServlet(name = "ManageProviderController", value = "/admin/providers/*")
public class ManageProviderController
		extends HttpServlet {
	private HttpSession session;
	private final ProviderServices providerServices = new ProviderServices();
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		providerServices.listProviders(request, response);
	}


	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
			case "create":
				providerServices.create(request, response);
				break;
			case "update":
				providerServices.update(request, response);
				break;
			case "delete":
				providerServices.delete(request, response);
				break;
		}
	}

}

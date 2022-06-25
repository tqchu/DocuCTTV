package com.ctvv.controller.admin;

import com.ctvv.service.AdminServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebServlet(name = "ManageAdminsController", value = "/admin/admins/*")
public class ManageAdminsController
		extends HttpServlet {
	private final AdminServices adminServices = new AdminServices();

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null) adminServices.listAdmins(request, response);
		else if ("create".equals(action)) {
			adminServices.showAddAdminForm(request, response);
		} else {
			adminServices.listAdmins(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                       UnsupportedEncodingException {
		String action = request.getParameter("action");
		switch (action) {
			case "create":
				adminServices.createAdmin(request, response);
				break;
			case "update":
				adminServices.updateAdmin(request, response);
				break;
			case "delete":
				adminServices.deleteAdmin(request, response);

		}
	}

}

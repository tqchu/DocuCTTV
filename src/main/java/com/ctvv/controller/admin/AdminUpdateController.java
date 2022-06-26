package com.ctvv.controller.admin;

import com.ctvv.service.AdminServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminUpdateController", value = "/admin/update")
public class AdminUpdateController
		extends HttpServlet {

	private final AdminServices adminServices = new AdminServices();

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		adminServices.showUpdateForm(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		adminServices.updateAccountInformation(request, response);
	}

}

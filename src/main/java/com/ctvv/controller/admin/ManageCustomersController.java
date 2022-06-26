
package com.ctvv.controller.admin;

import com.ctvv.service.CustomerServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ManageCustomersController", value = "/admin/customers/*")
public class ManageCustomersController
		extends HttpServlet {
	private CustomerServices customerServices = new CustomerServices();
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		customerServices.listCustomers(request, response);
	}



	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		customerServices.changeCustomerStatus(request,response);
	}
}

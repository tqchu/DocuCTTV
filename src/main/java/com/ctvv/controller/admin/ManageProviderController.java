package com.ctvv.controller.admin;

import com.ctvv.dao.ProviderDAO;
import com.ctvv.model.Provider;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ManageProviderController", value = "/admin/providers/*")
public class ManageProviderController
		extends HttpServlet {
	private ProviderDAO providerDAO;
    final String HOME_PAGE = "/admin/manage/home.jsp";
	final String SEARCH_SERVLET ="/admin/providers/search";
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.equals(request.getContextPath()+ SEARCH_SERVLET)){
			search(request,response);
		}
		else
			listProviders(request,response);
	}

	private void search(HttpServletRequest request, HttpServletResponse response) {

	}

	private void listProviders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		goHome(request, response);
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("tab", "providers");
		RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_PAGE);
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action){
			case "create":
				create(request,response);
				break;
			case "update":
				update(request, response);
				break;
			case "delete":
				delete(request, response);
				break;
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {

	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {

	}

	private void create(HttpServletRequest request, HttpServletResponse response) {

		String providerName = request.getParameter("name");
		String providerAddress = request.getParameter("address");
		String phoneNumber = request.getParameter("phoneNumber");
		String email = request.getParameter("email");
		String taxId = request.getParameter("taxId");

		boolean isNameValid = providerDAO.findByName(providerName) == null;
		boolean isPhoneNumberValid = providerDAO.findByPhoneNumber(phoneNumber) == null;
		boolean isEmailValid = providerDAO.findByEmail(email) == null;
		boolean isTaxIdValid = providerDAO.findByTaxId(taxId) == null;
		if (isNameValid && isPhoneNumberValid && isEmailValid && isTaxIdValid) {
			Provider provider = new Provider(providerName, providerAddress, phoneNumber, email, taxId);
			request.setAttribute("successMessage", "Thêm nhà sản xuất thành công");
			providerDAO.create(provider);
			try {
				response.sendRedirect(request.getContextPath() + "admin/providers");
			} catch (IOException e){
				e.printStackTrace();
			}
		} else {
			if(!isNameValid){
				request.setAttribute("providerNameErrorMessage", "Tên nhà cung cấp đã tồn tại, vui lòng chọn tên khác");
			}
			if(!isPhoneNumberValid) {
				request.setAttribute("phoneNumberErrorMessage", "Số điện thoại đã tồn tại, vui lòng chọn số điện thoại khác");
			}
			if(!isEmailValid){
				request.setAttribute("emailErrorMessage", "Email đã tồn tại, vui lòng chọn email khác");
			}
			if(!isTaxIdValid) {
				request.setAttribute("taxIdErrorMessage", "Mã số thuế đã tồn tại, vui lòng chọn mã số thuế khác");
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/providers");
			try {
				dispatcher.forward(request, response);
			} catch (IOException | ServletException e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}
}

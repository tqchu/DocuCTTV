package com.ctvv.service;

import com.ctvv.dao.ProviderDAO;
import com.ctvv.model.Provider;
import com.ctvv.util.RequestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ProviderServices {
	private HttpSession session;
	private final int NUMBER_OF_RECORDS_PER_MANAGE_PROVIDER_PAGE = 10;
	private final String MANAGE_HOME_PAGE = "/admin/manage/home.jsp";
	private ProviderDAO providerDAO = new ProviderDAO();

	public void listProviders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		String sortBy = getOrder(request);
		List<Provider> providerList;
		int begin = RequestUtils.getBegin(request, NUMBER_OF_RECORDS_PER_MANAGE_PROVIDER_PAGE);
		providerList = providerDAO.get(begin, NUMBER_OF_RECORDS_PER_MANAGE_PROVIDER_PAGE, keyword, sortBy, null);
		int numberOfPages = (providerDAO.count(keyword) - 1) / NUMBER_OF_RECORDS_PER_MANAGE_PROVIDER_PAGE + 1;
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("providerList", providerList);
		request.setAttribute("tab", "providers");
		RequestDispatcher dispatcher = request.getRequestDispatcher(MANAGE_HOME_PAGE);
		dispatcher.forward(request, response);
	}

	private String getOrder(HttpServletRequest request) {
		String sortBy = request.getParameter("sortBy");
		if (sortBy != null) {
			switch (sortBy) {
				case "default":
					sortBy = null;
					break;
				case "name":
					sortBy = "provider_name";
					break;
			}
		}
		return sortBy;
	}

	public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String providerName = request.getParameter("name");
		String providerAddress = request.getParameter("address");
		String phoneNumber = request.getParameter("phoneNumber");
		String email = request.getParameter("email");
		String taxId = request.getParameter("taxId");

		boolean isNameValid = (providerDAO.findByName(providerName) == null);
		boolean isPhoneNumberValid = (providerDAO.findByPhoneNumber(phoneNumber) == null);
		boolean isEmailValid = (providerDAO.findByEmail(email) == null);
		boolean isTaxIdValid = (providerDAO.findByTaxId(taxId) == null);
		if (isNameValid && isPhoneNumberValid && isEmailValid && isTaxIdValid) {
			Provider provider = new Provider(providerName, providerAddress, phoneNumber, email, taxId);
			providerDAO.create(provider);
			request.setAttribute("successMessage", "Thêm nhà cung cấp thành công");
			try {
				response.sendRedirect(request.getContextPath() + "/admin/providers");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String errorMessage = "";
			boolean isFirst = true;
			if (!isNameValid) {
				errorMessage += "Tên NCC";
				isFirst = false;
			}
			if (!isPhoneNumberValid) {
				if (isFirst) errorMessage += "Số điện thoại, ";
				else {
					errorMessage += ", số điện thoại";
					isFirst = false;
				}

			}
			if (!isEmailValid) {
				if (isFirst) errorMessage += "Email";
				else {
					errorMessage += ", email";
					isFirst = false;
				}
			}
			if (!isTaxIdValid) {
				if (isFirst) errorMessage += "MST";
				else {
					errorMessage += ", MST";
				}

			}
			errorMessage += " đã  tồn tại!";
			session.setAttribute("errorMessage", errorMessage);
			response.sendRedirect(request.getContextPath() + "/admin/providers");
		}
	}

	public void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int providerId = Integer.parseInt(request.getParameter("id"));
		// Tìm provider ứng với id
		Provider provider = providerDAO.get(providerId);
		String providerName = request.getParameter("name");
		String address = request.getParameter("address");
		String phoneNumber = request.getParameter("phoneNumber");
		String email = request.getParameter("email");
		String taxId = request.getParameter("taxId");
		session = request.getSession();

		// Lưu ý: nếu phần người dùng nhập không khác gì dữ liệu cũ thì khỏi cần find và trả về true, nếu dùng thì
		// tiếp tục kiểm tra điều kiện của find (để xem thử trùng với provider khác không)
		boolean isEmailValid =
				((Objects.equals(email, provider.getEmail())) || providerDAO.findByEmail(email) == null);
		boolean isTaxIdValid = (Objects.equals(taxId, provider.getTaxId())) || providerDAO.findByTaxId(taxId) == null;
		boolean isNameValid =
				(Objects.equals(providerName, provider.getProviderName())) || (providerDAO.findByName(providerName) == null);
		boolean isPhoneNumberValid =
				(Objects.equals(phoneNumber, provider.getPhoneNumber())) || (providerDAO.findByPhoneNumber(phoneNumber) == null);
		if (isEmailValid && isTaxIdValid && isNameValid && isPhoneNumberValid) {
			provider = new Provider(providerId, providerName, address,phoneNumber, email, taxId);
			providerDAO.update(provider);
			session.setAttribute("successMessage", "Cập nhật thành công!");
			try {
				response.sendRedirect(request.getContextPath() + "/admin/providers");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String errorMessage = "";
			boolean isFirst = true;
			if (!isNameValid) {
				errorMessage += "Tên NCC";
				isFirst = false;
			}
			if (!isPhoneNumberValid) {
				if (isFirst) errorMessage += "Số điện thoại, ";
				else {
					errorMessage += ", số điện thoại";
					isFirst = false;
				}
			}
			if (!isEmailValid) {
				if (isFirst) errorMessage += "Email";
				else {
					errorMessage += ", email";
					isFirst = false;
				}
			}
			if (!isTaxIdValid) {
				if (isFirst) errorMessage += "MST";
				else errorMessage += ", MST";
			}
			errorMessage += " đã  tồn tại!";
			session.setAttribute("errorMessage", errorMessage);
			response.sendRedirect(request.getContextPath() + "/admin/providers");
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		// Nhan parameter có name là "id"
		int providerId = Integer.parseInt(request.getParameter("id"));
		// goi ham delete
		providerDAO.delete(providerId);
		// set successMessage trong session
		session = request.getSession();
		session.setAttribute("successMessage", "Xóa nhà cung cấp thành công");
		// sendRedirect về trang chính + /admin/providers
		try {
			response.sendRedirect(request.getContextPath() + "/admin/providers");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
